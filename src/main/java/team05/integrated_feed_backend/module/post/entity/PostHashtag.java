package team05.integrated_feed_backend.module.post.entity;
import jakarta.persistence.*;
import lombok.*;
import team05.integrated_feed_backend.common.BaseEntity;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostHashtag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postHashtagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post; // Post와 다대일 관계

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id", nullable = false)
    private Hashtag hashtag; // Hashtag와 다대일 관계
}
