package team05.integrated_feed_backend.module.post.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import team05.integrated_feed_backend.common.BaseApiResponse;
import team05.integrated_feed_backend.common.code.StatusCode;
import team05.integrated_feed_backend.module.post.dto.request.PostStatisticsListReq;
import team05.integrated_feed_backend.module.post.dto.response.PostStatisticsListRes;
import team05.integrated_feed_backend.module.post.service.PostStatisticsService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/statistics")
public class PostStatisticsController implements PostStatisticsControllerDocs {

	private final PostStatisticsService postStatisticsService;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public BaseApiResponse<List<PostStatisticsListRes>> getPostStatistics(
		@RequestParam(defaultValue = "date") String type, @RequestParam(required = false) String hashtag,
		@RequestParam(required = false, defaultValue = "count") String value,
		@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
		@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
		PostStatisticsListReq request = new PostStatisticsListReq(type, hashtag, value, start, end);
		List<PostStatisticsListRes> res = postStatisticsService.getPostStatistics(request);
		return BaseApiResponse.of(StatusCode.OK, res);
	}
}
