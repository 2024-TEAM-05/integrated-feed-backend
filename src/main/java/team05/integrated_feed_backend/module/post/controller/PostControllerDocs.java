package team05.integrated_feed_backend.module.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import team05.integrated_feed_backend.common.BaseApiResponse;
import team05.integrated_feed_backend.common.dto.PaginationQuery;
import team05.integrated_feed_backend.module.post.dto.request.PostSearchReq;
import team05.integrated_feed_backend.module.post.dto.response.PostSearchRes;

@Tag(name = "Post", description = "피드 관련 API")
public interface PostControllerDocs {

	@Operation(summary = "게시물 조회", description = "게시물을 조회 조건에 따라 검색합니다.")
	@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.")
	BaseApiResponse<PostSearchRes> getPosts(
		PostSearchReq postSearchReq,
		PaginationQuery paginationQuery
	);

}