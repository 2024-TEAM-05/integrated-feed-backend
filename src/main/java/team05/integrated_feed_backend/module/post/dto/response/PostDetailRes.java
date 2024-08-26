package team05.integrated_feed_backend.module.post.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostDetailRes {

	@Schema(description = "게시물 id")
	private Long postId;

	@Schema(description = "게시물 제목")
	private String title;

	@Schema(description = "게시물 내용")
	private String content;

	@Schema(description = "조회 수")
	private Long viewCount;

	@Schema(description = "좋아요 수")
	private Long likeCount;

	@Schema(description = "공유 수")
	private Long shareCount;

	@Schema(description = "생성일")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private LocalDateTime createdAt;

	@Schema(description = "수정일")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private LocalDateTime updatedAt;

	@Schema(description = "해시태그 리스트")
	private List<HashtagDto> hashtags;

}
