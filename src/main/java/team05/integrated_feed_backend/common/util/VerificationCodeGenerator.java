package team05.integrated_feed_backend.common.util;

import java.security.SecureRandom;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class VerificationCodeGenerator {
	private static final int CODE_LENGTH = 6;
	private static final SecureRandom random = new SecureRandom();

	public static String generateCode() {
		// 인증 코드로 가능한 최소 숫자 (ex. 6자리라면 100_000)
		int min = (int)Math.pow(10, CODE_LENGTH - 1);
		// 인증 코드로 가능한 최대 숫자 (ex. 6자리라면 999_999)
		int max = (int)Math.pow(10, CODE_LENGTH) - 1;

		// min ~ max 사이의 숫자 중 하나를 랜덤하게 선택
		int code = min + random.nextInt(max - min);

		return String.valueOf(code);
	}
}
