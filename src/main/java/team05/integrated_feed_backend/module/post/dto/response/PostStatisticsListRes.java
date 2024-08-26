package team05.integrated_feed_backend.module.post.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostStatisticsListRes {
	private String date;
	private Long countByValue;

	public PostStatisticsListRes(String date, Long countByValue) {
		this.date = date;
		this.countByValue = countByValue;
	}
}
