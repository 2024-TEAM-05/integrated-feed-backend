package team05.integrated_feed_backend.module.post.entity;

import jakarta.persistence.*;
import lombok.*;
import team05.integrated_feed_backend.common.BaseEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Hashtag extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long hashtagId;

	@Column(name = "hashtag_name", nullable = false, unique = true)
	private String hashtagName;

	@OneToMany(mappedBy = "hashtag", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private List<PostHashtag> postHashtags = new ArrayList<>();

}
