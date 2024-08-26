package team05.integrated_feed_backend.infra.mail;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import team05.integrated_feed_backend.module.member.entity.vo.Email;

@Component
@Slf4j
public class SimpleMailService implements MailService {
	private static final String VERIFICATION_EMAIL_TITLE = "Integrated Feed 회원가입 인증코드";

	@Override
	public void sendVerificationCode(Email sendTo, String code) {
		String content = buildVerificationEmailContent(code);
		send(sendTo.getValue(), VERIFICATION_EMAIL_TITLE, content);
	}

	@Override
	public void send(String sendTo, String subject, String content) {
		log.info("Send mail to: {}\nSubject: {}\nContent: {}", sendTo, subject, content);
	}

	private String buildVerificationEmailContent(String code) {
		return "안녕하세요.\nIntegrated Feed의 이메일 등록을 위한 인증 메일입니다.\n\n인증번호는 " + code + " 입니다.";
	}
}
