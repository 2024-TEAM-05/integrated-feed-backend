package team05.integrated_feed_backend.module.post.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import team05.integrated_feed_backend.common.DataApiResponse;
import team05.integrated_feed_backend.common.dto.PaginationQuery;
import team05.integrated_feed_backend.module.post.dto.request.PostSearchReq;
import team05.integrated_feed_backend.module.post.dto.response.PostSearchRes;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController implements PostControllerDocs {

	@Override
	@GetMapping
	public DataApiResponse<PostSearchRes> getPosts(
		@ModelAttribute PostSearchReq postSearchReq,
		@Valid @ModelAttribute PaginationQuery paginationQuery) {

		PostSearchRes res = new PostSearchRes();

		return new DataApiResponse<>(res, HttpStatus.OK, "success");
	}

}
