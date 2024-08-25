package team05.integrated_feed_backend.module.member.entity;

import static team05.integrated_feed_backend.module.member.entity.enums.MemberStatus.*;

import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team05.integrated_feed_backend.common.BaseEntity;
import team05.integrated_feed_backend.module.member.entity.enums.MemberStatus;
import team05.integrated_feed_backend.module.member.entity.vo.Email;
import team05.integrated_feed_backend.module.member.entity.vo.Password;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class Member extends BaseEntity {
	public static final MemberStatus DEFAULT_MEMBER_STATUS = UNVERIFIED;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberId;

	@Column(nullable = false, unique = true)
	private String account;

	@Embedded
	@Column(nullable = false)
	private Password password;

	@Embedded
	@Column(nullable = false, unique = true)
	private Email email;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MemberStatus status;

	public static Member signUp(String account, String password, String email, PasswordEncoder passwordEncoder) {
		return Member.builder()
			.account(account)
			.password(Password.of(password, passwordEncoder))
			.email(Email.from(email))
			.status(DEFAULT_MEMBER_STATUS)
			.build();
	}
}
