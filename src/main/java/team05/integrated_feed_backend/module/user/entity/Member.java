package team05.integrated_feed_backend.module.user.entity;

import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.Embedded;
import team05.integrated_feed_backend.common.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false, unique = true)
    private String account;

    @Column(nullable = false)
    private String password;

	@Embedded
	@Column(nullable = false, unique = true)
	private Email email;

    @Column(nullable = false)
    private String status;
}
