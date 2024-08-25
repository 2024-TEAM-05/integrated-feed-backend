package team05.integrated_feed_backend.module.user.entity;

import static team05.integrated_feed_backend.module.user.entity.enums.MemberStatus.*;

import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import team05.integrated_feed_backend.common.BaseEntity;
import team05.integrated_feed_backend.module.user.entity.enums.MemberStatus;
import team05.integrated_feed_backend.module.user.entity.vo.Password;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Member extends BaseEntity {
	public static final MemberStatus DEFAULT_MEMBER_STATUS = UNVERIFIED;

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
}
