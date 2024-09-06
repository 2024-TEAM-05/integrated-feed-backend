package team05.integrated_feed_backend.module.member.entity;

import static org.junit.jupiter.api.Assertions.*;
import static team05.integrated_feed_backend.module.member.entity.enums.MemberStatus.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import team05.integrated_feed_backend.core.config.PasswordEncoderConfig;

@SpringJUnitConfig(PasswordEncoderConfig.class)
class MemberTest {

	@Autowired
	PasswordEncoder passwordEncoder;
	String account;
	String password;
	String email;

	@BeforeEach
	void setUp() {
		account = "user1234";
		password = "password12!";
		email = "email@gmail.com";
	}

	@Test
	@DisplayName("[성공] 회원가입을 성공한다")
	void createMemberSuccessfully() {
		// when
		Member actual = Member.signUp(account, password, email, passwordEncoder);

		// then
		assertEquals(actual.getAccount(), account);
		assertEquals(actual.getEmail(), email);
		assertTrue(passwordEncoder.matches(password, actual.getPassword()));
	}

	@Test
	@DisplayName("[성공] 회원가입 시 회원 상태는 UNVERIFIED로 설정된다")
	void memberStatusIsUnverifiedWhenSignUp() {
		// when
		Member actual = Member.signUp(account, password, email, passwordEncoder);

		// then
		assertEquals(actual.getStatus(), UNVERIFIED);
	}
}
