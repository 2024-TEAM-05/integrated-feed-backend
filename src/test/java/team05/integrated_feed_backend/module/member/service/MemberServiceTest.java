package team05.integrated_feed_backend.module.member.service;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.ThrowableAssert.*;
import static org.junit.jupiter.api.Assertions.*;
import static team05.integrated_feed_backend.common.code.StatusCode.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import team05.integrated_feed_backend.exception.custom.DuplicateResourceException;
import team05.integrated_feed_backend.module.member.dto.request.MemberSignupReq;
import team05.integrated_feed_backend.module.member.entity.Member;
import team05.integrated_feed_backend.module.member.repository.MemberRepository;

@SpringBootTest
@Transactional
class MemberServiceTest {

	@Autowired
	MemberService memberService;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Nested
	@DisplayName("회원 가입 테스트")
	class SignUpTest {

		String account;
		String password;
		String email;
		MemberSignupReq request;

		@BeforeEach
		void setUp() {
			account = "user1234";
			password = "password12!";
			email = "email@gmail.com";
			request = new MemberSignupReq(account, email, password);
		}

		@Test
		@DisplayName("[성공] 회원가입을 완료한다.")
		void signUpSuccessfully() {
			// when
			memberService.signUp(request);

			// then
			Optional<Member> actual = memberRepository.findByAccount(account);
			assertTrue(actual.isPresent());
			assertEquals(actual.get().getAccount(), account);
			assertEquals(actual.get().getEmail(), email);
		}

		@Test
		@DisplayName("[실패] 중복된 계정인 경우 회원가입에 실패한다.")
		void signUpFailedByDuplicateAccount() {
			// given
			String anotherEmail = "email2@gmail.com";
			Member anotherMember = Member.signUp(account, password, anotherEmail, passwordEncoder);

			memberRepository.save(anotherMember);

			// when
			ThrowingCallable signUp = () -> memberService.signUp(request);

			// then
			assertThatThrownBy(signUp)
				.isInstanceOf(DuplicateResourceException.class)
				.hasMessage(DUPLICATE_ACCOUNT.getMessage());
		}

		@Test
		@DisplayName("[실패] 중복된 이메일인 경우 회원가입에 실패한다.")
		void signUpFailedByDuplicateEmail() {
			// given
			String anotherAccount = "user5678";
			Member anotherMember = Member.signUp(anotherAccount, password, email, passwordEncoder);

			memberRepository.save(anotherMember);

			// when
			ThrowingCallable signUp = () -> memberService.signUp(request);

			// then
			assertThatThrownBy(signUp)
				.isInstanceOf(DuplicateResourceException.class)
				.hasMessage(DUPLICATE_EMAIL.getMessage());
		}
	}
}
