package team05.integrated_feed_backend.module.user.entity.vo;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.ThrowableAssert.*;
import static team05.integrated_feed_backend.common.code.StatusCode.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import team05.integrated_feed_backend.exception.custom.BadRequestException;

class EmailTest {

	@ParameterizedTest
	@ValueSource(strings = {"user1234@gmail.com", "firstname.lastname@company.co.uk", "jane_doe123@sub.example.org"})
	@DisplayName("[성공] 이메일을 생성한다.")
	void createEmailSuccessfully(String email) {
		// when
		Email actual = Email.from(email);

		// then
		assertThat(actual.getValue()).isEqualTo(email);
	}

	@ParameterizedTest
	@ValueSource(strings = {"plainaddress", "@missingusername.com", "username@domain@domain.com"})
	@DisplayName("[실패] 이메일 형식이 올바르지 않아 생성에 실패한다.")
	void shouldThrowExceptionWhenEmailFormatIsInvalid(String email) {
		// when
		ThrowingCallable create = () -> Email.from(email);

		// then
		assertThatThrownBy(create).isInstanceOf(BadRequestException.class)
			.hasMessage(INVALID_EMAIL_FORMAT.getMessage());
	}

	@Test
	@DisplayName("[성공] 같은 이메일 주소를 가지고 있으면 같은 객체로 인식한다.")
	void sameEmailAddressesAreEqual() {
		// given
		String address = "email@gmail.com";
		Email email1 = Email.from(address);
		Email email2 = Email.from(address);

		// when
		boolean result = email1.equals(email2);

		// then
		assertThat(result).isTrue();
	}
}
