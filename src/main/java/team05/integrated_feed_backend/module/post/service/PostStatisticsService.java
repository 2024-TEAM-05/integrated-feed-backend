package team05.integrated_feed_backend.module.post.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team05.integrated_feed_backend.module.post.dto.request.PostStatisticsListReq;
import team05.integrated_feed_backend.module.post.dto.response.PostStatisticsListRes;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostStatisticsService {
	public List<PostStatisticsListRes> getPostStatistics(PostStatisticsListReq request) {
		// 예외 처리
		// 요청에 따른 통계 데이터 조회
		// 일단 repository에서 메소드가 완성이 안되어서 List.of()로 넣었습니다.
		List<PostStatisticsListRes> statistics = List.of();
		return statistics;
	}
}
