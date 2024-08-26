package team05.integrated_feed_backend.module.post.controller;

import java.time.LocalDate;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import team05.integrated_feed_backend.common.BaseApiResponse;
import team05.integrated_feed_backend.module.post.dto.response.PostStatisticsListRes;

@Tag(name = "PostStatistics", description = "통계 관련 API")
public interface PostStatisticsControllerDocs {
	// 쿼리 파라미터 문서화
	@Operation(summary = "통계 조회")
	@ApiResponse(responseCode = "200", description = "조건에 맞는 통계가 생성되어 반환됩니다.")
	BaseApiResponse<List<PostStatisticsListRes>> getPostStatistics(
		@Parameter(name = "type", description = "조회 타입(날짜 or 시간)")
		String type,

		@Parameter(name = "hashtag", description = "조회하고 싶은 해시태그")
		String hashtag,

		@Parameter(name = "value", description = "조회한 게시물 통계 정보 (게시물 수, 조회 수, 좋아요 수, 공유 수)")
		String value,

		@Parameter(name = "start", description = "조회 기준 시작일")
		LocalDate start,

		@Parameter(name = "end", description = "조회 기준 종료일")
		LocalDate end);
}
