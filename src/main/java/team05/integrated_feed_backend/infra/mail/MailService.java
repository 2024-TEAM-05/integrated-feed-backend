package team05.integrated_feed_backend.infra.mail;

public interface MailService {

	void sendVerificationCode(String sendTo, String code);

	void send(String sendTo, String subject, String content);
}
