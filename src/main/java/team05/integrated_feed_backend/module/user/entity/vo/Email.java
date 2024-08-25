package team05.integrated_feed_backend.module.user.entity.vo;

import static team05.integrated_feed_backend.common.code.StatusCode.*;

import java.util.regex.Pattern;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team05.integrated_feed_backend.exception.custom.BadRequestException;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Email {
	private static final Pattern EMAIL_PATTERN = Pattern.compile(
		"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");

	@Column(name = "email", nullable = false, unique = true)
	private final String value;

	private Email(String value) {
		validate(value);
		this.value = value;
	}

	public static Email from(String email) {
		return new Email(email);
	}

	private void validate(String value) {
		if (!isValidEmail(value)) {
			throw new BadRequestException(INVALID_EMAIL_FORMAT);
		}
	}

	private boolean isValidEmail(String value) {
		return EMAIL_PATTERN.matcher(value).matches();
	}
}
