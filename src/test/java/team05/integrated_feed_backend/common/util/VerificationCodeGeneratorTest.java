package team05.integrated_feed_backend.common.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VerificationCodeGeneratorTest {

	@Test
	@DisplayName("[성공] 6자리 인증코드를 생성한다.")
	void generateVerificationCodeSuccessfully() {
		// when
		String actual = VerificationCodeGenerator.generateCode();

		// then
		assertEquals(actual.length(), 6);
	}
}
