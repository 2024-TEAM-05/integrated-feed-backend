package team05.integrated_feed_backend.module.post.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team05.integrated_feed_backend.common.entity.BaseEntity;

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
