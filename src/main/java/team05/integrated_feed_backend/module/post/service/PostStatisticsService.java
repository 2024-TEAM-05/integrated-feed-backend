package team05.integrated_feed_backend.module.post.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team05.integrated_feed_backend.module.post.dto.request.PostStatisticsListReq;
import team05.integrated_feed_backend.module.post.dto.response.PostStatisticsListRes;
import team05.integrated_feed_backend.module.post.repository.PostRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostStatisticsService {

	private final PostRepository postRepository;

	public List<PostStatisticsListRes> getPostStatistics(PostStatisticsListReq request) {
		// 예외 처리
		// 요청에 따른 통계 데이터 조회
		List<PostStatisticsListRes> statistics = postRepository.findPostStatisticsByQueryParameter(request);
		return statistics;
	}
}
