package team05.integrated_feed_backend.module.post.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import team05.integrated_feed_backend.module.post.entity.Post;

@Getter
@Builder
public class PostDto {

	@Schema(description = "게시물 id")
	@JsonProperty("post_id")
	private Long postId;

	@Schema(description = "게시물 제목")
	@JsonProperty("post_title")
	private String title;

	@Schema(description = "게시물 내용")
	@JsonProperty("post_content")
	private String content;

	@Schema(description = "조회 수")
	@JsonProperty("view_count")
	private Long viewCount;

	@Schema(description = "좋아요 수")
	@JsonProperty("like_count")
	private Long likeCount;

	@Schema(description = "공유 수")
	@JsonProperty("share_count")
	private Long shareCount;

	@Schema(description = "생성일")
	@JsonProperty("created_at")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private LocalDateTime createdAt;

	@Schema(description = "수정일")
	@JsonProperty("updated_at")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private LocalDateTime updatedAt;

	@Schema(description = "해시태그 리스트")
	private List<HashtagDto> hashtags;

	public static PostDto from(Post post) {
		List<HashtagDto> hashtags = post.getPostHashtags().stream()
			.map(postHashtag -> HashtagDto.of(postHashtag.getHashtag().getHashtagId(),
				postHashtag.getHashtag().getHashtagName()))
			.toList();

		return PostDto.builder()
			.postId(post.getPostId())
			.title(post.getTitle())
			.content(post.getContent())
			.viewCount(post.getViewCount())
			.likeCount(post.getLikeCount())
			.shareCount(post.getShareCount())
			.createdAt(post.getCreatedAt())
			.updatedAt(post.getUpdatedAt())
			.hashtags(hashtags)
			.build();
	}

}
