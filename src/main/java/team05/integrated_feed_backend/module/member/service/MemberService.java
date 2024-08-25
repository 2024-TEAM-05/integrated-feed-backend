package team05.integrated_feed_backend.module.member.service;

import static team05.integrated_feed_backend.common.code.StatusCode.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team05.integrated_feed_backend.exception.custom.DuplicateResourceException;
import team05.integrated_feed_backend.module.member.dto.request.MemberSignupReq;
import team05.integrated_feed_backend.module.member.entity.Member;
import team05.integrated_feed_backend.module.member.entity.vo.Email;
import team05.integrated_feed_backend.module.member.event.publisher.MemberEventPublisher;
import team05.integrated_feed_backend.module.member.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
	private final PasswordEncoder passwordEncoder;
	private final MemberRepository memberRepository;
	private final MemberEventPublisher memberEventPublisher;

	@Transactional
	public void signUp(MemberSignupReq request) {
		// 계정 중복 검사
		validateAccountUnique(request.account());
		// 이메일 중복 검사
		validateEmailUnique(request.email());

		// 회원 생성 및 저장
		Member member = Member.signUp(request.account(), request.password(), request.email(), passwordEncoder);
		memberRepository.save(member);

		// 회원 가입 완료 이벤트 생성
		memberEventPublisher.publishSignedUpEvent(member);
	}

	private void validateAccountUnique(String account) {
		if (memberRepository.existsByAccount(account)) {
			throw new DuplicateResourceException(DUPLICATE_ACCOUNT);
		}
	}

	private void validateEmailUnique(String email) {
		if (memberRepository.existsByEmail(Email.from(email))) {
			throw new DuplicateResourceException(DUPLICATE_EMAIL);
		}
	}
}
