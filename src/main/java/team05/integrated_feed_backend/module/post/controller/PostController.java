package team05.integrated_feed_backend.module.post.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import team05.integrated_feed_backend.common.BaseApiResponse;
import team05.integrated_feed_backend.common.code.StatusCode;
import team05.integrated_feed_backend.module.post.dto.request.PostSearchReq;
import team05.integrated_feed_backend.module.post.dto.response.PostDetailRes;
import team05.integrated_feed_backend.module.post.dto.response.PostSearchRes;
import team05.integrated_feed_backend.module.post.service.PostService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController implements PostControllerDocs {

	private final PostService postService;

	@Override
	@GetMapping
	public BaseApiResponse<PostSearchRes> getPosts(
		@ModelAttribute PostSearchReq postSearchReq
	) {

		PostSearchRes res = new PostSearchRes();

		return BaseApiResponse.of(StatusCode.OK, res);
	}

	@Override
	@GetMapping("/{id}")
	public BaseApiResponse<PostDetailRes> getPostDetail(
		@PathVariable @NotNull Long id
	) {

		PostDetailRes res = postService.getPostDetail(id);

		return BaseApiResponse.of(StatusCode.OK, res);

	}

}
