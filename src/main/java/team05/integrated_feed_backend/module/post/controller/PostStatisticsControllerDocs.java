package team05.integrated_feed_backend.module.post.controller;

import java.time.LocalDate;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import team05.integrated_feed_backend.common.BaseApiResponse;
import team05.integrated_feed_backend.module.post.dto.response.PostStatisticsListRes;

@Tag(name = "PostStatistics", description = "통계 관련 API")
public interface PostStatisticsControllerDocs {
	@Operation(summary = "통계 조회")
	@ApiResponse(responseCode = "200", description = "조건에 맞는 통계가 생성되어 반환됩니다.")
	BaseApiResponse<List<PostStatisticsListRes>> getPostStatistics(
		String type,
		String hashtag,
		String value,
		LocalDate start,
		LocalDate end);
}
