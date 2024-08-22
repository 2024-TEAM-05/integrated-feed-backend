package team05.integrated_feed_backend.module.auth.entity;

import jakarta.persistence.*;
import lombok.*;
import team05.integrated_feed_backend.core.entity.BaseEntity;
import team05.integrated_feed_backend.module.user.entity.User;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerificationCode extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long verificationCodeId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private LocalDateTime expiredAt;
}