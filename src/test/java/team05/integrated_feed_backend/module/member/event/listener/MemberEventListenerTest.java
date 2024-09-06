package team05.integrated_feed_backend.module.member.event.listener;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import team05.integrated_feed_backend.module.auth.entity.VerificationCode;
import team05.integrated_feed_backend.module.auth.repository.VerificationCodeRepository;
import team05.integrated_feed_backend.module.member.entity.Member;
import team05.integrated_feed_backend.module.member.event.SignedUpEvent;
import team05.integrated_feed_backend.module.member.repository.MemberRepository;

@SpringBootTest
@Transactional
class MemberEventListenerTest {

	@Autowired
	MemberEventListener memberEventListener;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	VerificationCodeRepository verificationCodeRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Nested
	@DisplayName("회원가입 이벤트 처리")
	class HandleSignUpdEvent {
		Member member;
		SignedUpEvent event;

		@BeforeEach
		void setUp() {
			MockitoAnnotations.openMocks(this);

			member = memberRepository.save(
				Member.signUp("user1234", "password12!", "email@gmail.com", passwordEncoder));
			event = new SignedUpEvent(member);
		}

		@Test
		@DisplayName("[성공] 회원가입 시 인증 코드를 생성한다")
		void createVerificationCodeSuccessfully() {
			// When
			memberEventListener.handleSignedUpEvent(event);

			// Then
			Optional<VerificationCode> actual = verificationCodeRepository.findByMember(member);
			assertTrue(actual.isPresent());
			assertEquals(actual.get().getMember(), member);
		}
	}

}
