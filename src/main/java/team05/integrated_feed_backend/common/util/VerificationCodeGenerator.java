package team05.integrated_feed_backend.common.util;

import java.security.SecureRandom;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class VerificationCodeGenerator {
	private static final int CODE_LENGTH = 6;
	private static final SecureRandom random = new SecureRandom();

	public static String generateCode() {
		StringBuilder codeBuilder = new StringBuilder();
		for (int i = 0; i < CODE_LENGTH; i++) {
			int digit = random.nextInt(10); // 0부터 9까지 랜덤한 숫자 선택
			codeBuilder.append(digit);
		}
		return codeBuilder.toString();
	}
}
