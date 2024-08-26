package team05.integrated_feed_backend.module.post.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import team05.integrated_feed_backend.common.BaseApiResponse;
import team05.integrated_feed_backend.common.code.StatusCode;
import team05.integrated_feed_backend.module.post.dto.request.PostSearchReq;
import team05.integrated_feed_backend.module.post.dto.response.PostSearchRes;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController implements PostControllerDocs {

	@Override
	@GetMapping
	public BaseApiResponse<PostSearchRes> getPosts(
		@ModelAttribute PostSearchReq postSearchReq
	) {

		PostSearchRes res = new PostSearchRes();

		return new BaseApiResponse<>(HttpStatus.OK, StatusCode.OK.getMessage(), res);
	}

}
