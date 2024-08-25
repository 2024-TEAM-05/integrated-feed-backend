package team05.integrated_feed_backend.module.post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostDetailRes {

	@Schema(description = "게시물 상세 정보")
	private PostDto post;

}
