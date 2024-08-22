package team05.integrated_feed_backend.module.post.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
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
	private int viewCount;

	@Schema(description = "좋아요 수")
	@JsonProperty("like_count")
	private int likeCount;

	@Schema(description = "공유 수")
	@JsonProperty("share_count")
	private int shareCount;

	@Schema(description = "생성일")
	@JsonProperty("created_at")
	private LocalDateTime createdAt;

	@Schema(description = "수정일")
	@JsonProperty("updated_at")
	private LocalDateTime updatedAt;

	@Schema(description = "해시태그 리스트")
	private List<HashtagDto> hashtags;

}
