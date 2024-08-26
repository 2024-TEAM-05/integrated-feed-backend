package team05.integrated_feed_backend.module.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import team05.integrated_feed_backend.common.BaseApiResponse;
import team05.integrated_feed_backend.module.post.dto.request.PostSearchReq;
import team05.integrated_feed_backend.module.post.dto.response.PostSearchRes;

@Tag(name = "Post", description = "피드 관련 API")
public interface PostControllerDocs {

	@Operation(summary = "게시물 목록 조회", description = "게시물을 조회 조건에 따라 검색합니다.")
	@ApiResponse(responseCode = "200", description = "요청이 성공했습니다.")
	BaseApiResponse<PostSearchRes> getPosts(
		PostSearchReq postSearchReq
	);

	@Operation(summary = "게시물 좋아요 수 올리기")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "게시물 좋아요 수가 증가되었습니다.", useReturnTypeSchema = true),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 게시물입니다.", useReturnTypeSchema = true),
	})
	BaseApiResponse<Void> increaseLikeCount(Long postId);

	@Operation(summary = "게시물 공유 수 올리기")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "게시물 공유 수가 증가되었습니다.", useReturnTypeSchema = true),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 게시물입니다.", useReturnTypeSchema = true),
	})
	BaseApiResponse<Void> increaseShareCount(Long postId);
}