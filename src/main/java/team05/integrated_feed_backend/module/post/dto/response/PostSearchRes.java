package team05.integrated_feed_backend.module.post.dto.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team05.integrated_feed_backend.common.dto.PaginationMetadata;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostSearchRes {

	@Schema(description = "게시물 리스트")
	private List<PostDto> posts;

	@Schema(description = "페이지 정보")
	private PaginationMetadata page;

}
