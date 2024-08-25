package team05.integrated_feed_backend.module.auth.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team05.integrated_feed_backend.common.BaseEntity;
import team05.integrated_feed_backend.module.user.entity.Member;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class VerificationCode extends BaseEntity {
	private static final int EXPIRE_IN_DAYS = 1;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long verificationCodeId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@Column(nullable = false)
	private String code;

	@Column(nullable = false)
	private LocalDateTime expiredAt;

	public static VerificationCode of(Member member, String code, LocalDateTime issuedAt) {
		return VerificationCode.builder()
			.member(member)
			.code(code)
			.expiredAt(calculateExpiredAt(issuedAt))
			.build();
	}

	private static LocalDateTime calculateExpiredAt(LocalDateTime issuedAt) {
		return issuedAt.plusDays(EXPIRE_IN_DAYS);
	}
}
