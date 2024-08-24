package team05.integrated_feed_backend.module.post.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import team05.integrated_feed_backend.common.BaseApiResponse;
import team05.integrated_feed_backend.exception.code.StatusCode;
import team05.integrated_feed_backend.module.post.dto.request.PostStatisticsListReq;
import team05.integrated_feed_backend.module.post.dto.response.PostStatisticsListRes;
import team05.integrated_feed_backend.module.post.service.PostStatisticsService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostStatisticsController {

	private final PostStatisticsService postStatisticsService;

	@GetMapping("/statistics")
	public BaseApiResponse<List<PostStatisticsListRes>> getPostStatistics(
		@RequestParam(defaultValue = "date") String type, @RequestParam(required = false) String hashtag,
		@RequestParam(required = false, defaultValue = "count") String value,
		@RequestParam(required = false) String start, @RequestParam(required = false) String end) {
		PostStatisticsListReq request = new PostStatisticsListReq(type, hashtag, value, start, end);
		List<PostStatisticsListRes> res = postStatisticsService.getPostStatistics(request);
		return new BaseApiResponse<>(StatusCode.OK.getHttpStatus(), StatusCode.OK.getMessage(), res);
	}
}
