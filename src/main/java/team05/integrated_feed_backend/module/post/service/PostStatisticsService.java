package team05.integrated_feed_backend.module.post.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team05.integrated_feed_backend.common.code.StatusCode;
import team05.integrated_feed_backend.exception.custom.BadRequestException;
import team05.integrated_feed_backend.module.post.dto.request.PostStatisticsListReq;
import team05.integrated_feed_backend.module.post.dto.response.PostStatisticsListRes;
import team05.integrated_feed_backend.module.post.repository.PostRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostStatisticsService {
	private static final int MAX_DATE_RANGE_DAYS = 31;
	private static final int MAX_DATE_RANGE_DAYS_WITH_HOUR = 8;

	private final PostRepository postRepository;

	public List<PostStatisticsListRes> getPostStatistics(PostStatisticsListReq request) {
		if (!request.getStart().isBefore(request.getEnd())) {
			throw new BadRequestException(StatusCode.INVALID_STATISTICS_DATE);
		}

		String currentType = request.getType();
		LocalDateTime currentStart = request.getStart();
		LocalDateTime currentEnd = request.getEnd();

		if ("date".equals(currentType)) {
			if (!currentEnd.minusDays(MAX_DATE_RANGE_DAYS).isBefore(currentStart)) {
				throw new BadRequestException(StatusCode.EXCEEDED_STATISTICS_DATE_RANGE);
			}
			return postRepository.findPostStatisticsByQueryParameter(request);
		}
		if ("hour".equals(currentType)) {
			if (!currentEnd.minusDays(MAX_DATE_RANGE_DAYS_WITH_HOUR).isBefore(currentStart)) {
				throw new BadRequestException(StatusCode.EXCEEDED_STATISTICS_DATE_RANGE);
			}
			return postRepository.findPostStatisticsByQueryParameterWithHour(request);
		}
		throw new BadRequestException(StatusCode.NOT_SUPPORTED_STATISTICS_TYPE);
	}
}
