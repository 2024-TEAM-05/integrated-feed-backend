package team05.integrated_feed_backend.infra.mail;

import team05.integrated_feed_backend.module.member.entity.vo.Email;

public interface MailService {

	void sendVerificationCode(Email sendTo, String code);

	void send(String sendTo, String subject, String content);
}
