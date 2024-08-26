package team05.integrated_feed_backend.module.post.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import team05.integrated_feed_backend.common.enums.SocialMediaType;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Post extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	@Builder.Default
	private List<PostHashtag> postHashtags = new ArrayList<>();

	// 조회 수 증가 메서드
	public void incrementViewCount() {
		this.viewCount += 1;
	}

	// 좋아요 수 증가시키는 메서드
	public void increaseLikeCount() {
		this.likeCount += 1;
	}

	// 공유 수 증가시키는 메서드
	public void increaseShareCount() {
		this.shareCount += 1;
	}
}
