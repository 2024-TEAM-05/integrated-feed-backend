package team05.integrated_feed_backend.module.member.event.listener;

import java.time.LocalDateTime;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import team05.integrated_feed_backend.common.util.VerificationCodeGenerator;
import team05.integrated_feed_backend.infra.mail.MailService;
import team05.integrated_feed_backend.module.auth.entity.VerificationCode;
import team05.integrated_feed_backend.module.auth.repository.VerificationCodeRepository;
import team05.integrated_feed_backend.module.member.entity.Member;
import team05.integrated_feed_backend.module.member.event.SignedUpEvent;

@Component
@RequiredArgsConstructor
public class MemberEventListener {
	private final MailService mailService;
	private final VerificationCodeRepository verificationCodeRepository;

	@EventListener
	public void handleSignedUpEvent(SignedUpEvent event) {
		Member member = event.member();

		// 인증 코드 생성
		VerificationCode code = createVerificationCode(member);

		// 인증 코드 이메일 발송
		mailService.sendVerificationCode(member.getEmail(), code.getCode());
	}

	private VerificationCode createVerificationCode(Member member) {
		String code = VerificationCodeGenerator.generateCode();
		VerificationCode verificationCode = VerificationCode.of(member, code, LocalDateTime.now());

		return verificationCodeRepository.save(verificationCode);
	}
}
