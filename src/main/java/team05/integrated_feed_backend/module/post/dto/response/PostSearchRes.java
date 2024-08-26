package team05.integrated_feed_backend.module.post.dto.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import team05.integrated_feed_backend.common.dto.PaginationMetadata;
import team05.integrated_feed_backend.module.post.dto.PostDto;
import team05.integrated_feed_backend.module.post.entity.Post;

@Getter
@Builder
@AllArgsConstructor
public class PostSearchRes {

	@Schema(description = "게시물 리스트")
	private List<PostDto> posts;

	@Schema(description = "페이지 정보")
	private PaginationMetadata metadata;

	public static PostSearchRes of(List<Post> posts, PaginationMetadata metadata) {
		List<PostDto> postDtos = posts.stream()
			.map(PostDto::from) // Use the static factory method from PostDto
			.toList();
		return PostSearchRes.builder()
			.posts(postDtos)
			.metadata(metadata)
			.build();
	}

}
