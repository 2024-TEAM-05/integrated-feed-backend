package team05.integrated_feed_backend.module.post.entity;

import jakarta.persistence.*;
import lombok.*;
import team05.integrated_feed_backend.core.entity.BaseEntity;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "hashcode")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hashtag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private Long hashtagId;

    @Column(nullable = false, unique = true)
    private String hashtag;

    @OneToMany(mappedBy = "hashtag", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PostHashtag> postHashtags = new HashSet<PostHashtag>();  // Hashtag와 PostHashtag 간의 일대다 관계
}
