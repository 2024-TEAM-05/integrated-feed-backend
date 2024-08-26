package team05.integrated_feed_backend.module.post.repository;

import java.util.List;

import team05.integrated_feed_backend.module.post.dto.request.PostStatisticsListReq;
import team05.integrated_feed_backend.module.post.dto.response.PostStatisticsListRes;

public interface PostStatisticsCustomRepository {
	List<PostStatisticsListRes> findPostStatisticsByQueryParameter(PostStatisticsListReq request);

	List<PostStatisticsListRes> findPostStatisticsByQueryParameterWithHour(PostStatisticsListReq request);
}
