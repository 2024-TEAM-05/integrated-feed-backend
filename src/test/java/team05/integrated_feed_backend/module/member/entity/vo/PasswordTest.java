package team05.integrated_feed_backend.module.member.entity.vo;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.ThrowableAssert.*;
import static org.junit.jupiter.api.Assertions.*;
import static team05.integrated_feed_backend.common.code.StatusCode.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import team05.integrated_feed_backend.exception.custom.BadRequestException;

@SpringBootTest
class PasswordTest {
	@Autowired
	PasswordEncoder passwordEncoder;

	@ParameterizedTest
	@ValueSource(strings = {"password12!", "password55"})
	@DisplayName("[성공] 비밀번호를 생성한다")
	void createPasswordSuccessfully(String password) {
		// when
		Password actual = Password.of(password, passwordEncoder);

		// then
		assertTrue(passwordEncoder.matches(password, actual.getValue()));
	}

	@Test
	@DisplayName("[실패] 10자 이하의 비밀번호는 생성할 수 없다.")
	void shouldThrowExceptionWhenPasswordIsShort() {
		// given
		String shortPassword = "short";

		// when
		ThrowingCallable create = () -> Password.of(shortPassword, passwordEncoder);

		// then
		assertThatThrownBy(create).isInstanceOf(BadRequestException.class)
			.hasMessage(SHORT_PASSWORD.getMessage());
	}

	@ParameterizedTest
	@ValueSource(strings = {"1234567890", "abcdefghij", "!@#$%^&*()"})
	@DisplayName("[실패] 숫자, 문자, 특수문자 중 2가지 이상을 포함하지 않는 비밀번호는 생성할 수 없다.")
	void shouldThrowExceptionWhenPasswordContainsSingleTypeCharacters(String password) {
		// when
		ThrowingCallable create = () -> Password.of(password, passwordEncoder);

		// then
		assertThatThrownBy(create).isInstanceOf(BadRequestException.class)
			.hasMessage(SIMPLE_PASSWORD.getMessage());
	}

	@ParameterizedTest
	@ValueSource(strings = {"password111", "password1AAA", "password@@@"})
	@DisplayName("[실패] 3자 이상의 연속된 문자를 포함하는 비밀번호는 생성할 수 없다.")
	void shouldThrowExceptionWhenPasswordHasRepeatingCharacters(String password) {
		// when
		ThrowingCallable create = () -> Password.of(password, passwordEncoder);

		// then
		assertThatThrownBy(create).isInstanceOf(BadRequestException.class)
			.hasMessage(PASSWORD_HAS_REPEATING_CHARACTER.getMessage());
	}
}
