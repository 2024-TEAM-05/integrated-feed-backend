package team05.integrated_feed_backend.module.post.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import team05.integrated_feed_backend.common.BaseApiResponse;
import team05.integrated_feed_backend.exception.code.StatusCode;
import team05.integrated_feed_backend.module.post.PostService;
import team05.integrated_feed_backend.module.post.dto.request.PostSearchReq;
import team05.integrated_feed_backend.module.post.dto.response.PostSearchRes;

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

		return BaseApiResponse.of(StatusCode.OK, postService.getPosts(postSearchReq));
	}

}
