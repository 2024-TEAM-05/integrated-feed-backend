package team05.integrated_feed_backend.module.post.entity;

import jakarta.persistence.*;
import lombok.*;
import team05.integrated_feed_backend.common.enums.SocialMediaType;
import team05.integrated_feed_backend.core.entity.BaseEntity;

import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SocialMediaType type;

    @Column(nullable = false)
    private Long viewCount;

    @Column(nullable = false)
    private Long likeCount;

    @Column(nullable = false)
    private Long shareCount;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PostHashtag> postHashtags = new HashSet<PostHashtag>(); // Post와 PostHashtag 간 일대다
}
