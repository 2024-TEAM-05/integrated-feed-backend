package team05.integrated_feed_backend.module.auth.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team05.integrated_feed_backend.exception.code.StatusCode;
import team05.integrated_feed_backend.module.member.entity.Member;
import team05.integrated_feed_backend.module.member.repository.MemberRepository;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
		Member member = memberRepository.findByAccount(account)
			.orElseThrow(() -> new UsernameNotFoundException(StatusCode.USER_NOT_FOUND.name()));

		log.info("유저 조회 성공: {}", member.getAccount());
		return new CustomUserDetails(member);
	}
}
