package team05.integrated_feed_backend.module.post.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import team05.integrated_feed_backend.common.BaseApiResponse;
import team05.integrated_feed_backend.common.code.StatusCode;
import team05.integrated_feed_backend.module.post.dto.request.PostSearchReq;
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
		@ModelAttribute @Valid PostSearchReq postSearchReq
	) {
		String memberAccount = "원티드";
		postSearchReq.setDefaultHashtagIfEmpty(memberAccount);
		return BaseApiResponse.of(StatusCode.OK, postService.getPosts(postSearchReq));
	}

	// 좋아요 수 증가시키는 api
	@ResponseStatus(HttpStatus.OK)
	@PatchMapping("/{postId}/like")
	public BaseApiResponse<Void> increaseLikeCount(@PathVariable(name = "postId") Long postId) {
		postService.increaseLikeCount(postId);
		return BaseApiResponse.of(StatusCode.OK);
	}

	// 공유 수 증가시키는 api
	@ResponseStatus(HttpStatus.OK)
	@PatchMapping("/{postId}/share")
	public BaseApiResponse<Void> increaseShareCount(@PathVariable(name = "postId") Long postId) {
		postService.increaseShareCount(postId);
		return BaseApiResponse.of(StatusCode.OK);
	}

}
