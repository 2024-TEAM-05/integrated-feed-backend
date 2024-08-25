package team05.integrated_feed_backend.module.user.entity.vo;

import static team05.integrated_feed_backend.common.code.StatusCode.*;

import java.util.regex.Pattern;

import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team05.integrated_feed_backend.exception.custom.BadRequestException;

@Embeddable
@Getter
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Password {
	private static final int MIN_PASSWORD_LENGTH = 10;
	private static final Pattern SINGLE_TYPE_CHARACTER_PATTERN = Pattern.compile(
		"^\\d+$|^[a-zA-Z]+$|^[!@#$%^&*(),.?\":{}|<>]+$");

	@Column(name = "password", nullable = false, unique = true)
	private final String value;

	public static Password of(String plainPassword, PasswordEncoder passwordEncoder) {
		validate(plainPassword);
		return new Password(encrypt(passwordEncoder, plainPassword));
	}

	private static void validate(String plainPassword) {
		validateLength(plainPassword);
		validateTypeOfCharacters(plainPassword);
		validateNoRepeatingCharacters(plainPassword);
	}

	private static void validateLength(String plainPassword) {
		if (isShort(plainPassword)) {
			throw new BadRequestException(SHORT_PASSWORD);
		}
	}

	private static void validateTypeOfCharacters(String plainPassword) {
		if (containsSingleTypeCharacters(plainPassword)) {
			throw new BadRequestException(SIMPLE_PASSWORD);
		}
	}

	private static void validateNoRepeatingCharacters(String plainPassword) {
		if (hasRepeatingCharacter(plainPassword)) {
			throw new BadRequestException(PASSWORD_HAS_REPEATING_CHARACTER);
		}
	}

	private static boolean isShort(String plainPassword) {
		return plainPassword.length() < MIN_PASSWORD_LENGTH;
	}

	private static boolean containsSingleTypeCharacters(String plainPassword) {
		return SINGLE_TYPE_CHARACTER_PATTERN.matcher(plainPassword).matches();
	}

	private static boolean hasRepeatingCharacter(String plainPassword) {
		for (int i = 2; i < plainPassword.length(); ++i) {
			if (plainPassword.charAt(i) == plainPassword.charAt(i - 1)
				&& plainPassword.charAt(i) == plainPassword.charAt(i - 2)) {
				return true;
			}
		}

		return false;
	}

	private static String encrypt(PasswordEncoder passwordEncoder, String plainPassword) {
		return passwordEncoder.encode(plainPassword);
	}
}
