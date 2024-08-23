package team05.integrated_feed_backend.module.post.dto.request;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import team05.integrated_feed_backend.common.util.DateTimeParser;

@Getter
@RequiredArgsConstructor
public class PostStatisticsListReq {
	private String type;
	private String hashtag;
	private String value;
	private LocalDateTime start;
	private LocalDateTime end;

	public PostStatisticsListReq(String type, String hashtag, String value, String start, String end) {
		this.type = type;
		this.hashtag = hashtag;
		this.value = value;
		this.start = (start == null || start.isEmpty() ? LocalDateTime.now().minusDays(7) :
			DateTimeParser.parseDateTime(start));
		this.end = (end == null || end.isEmpty() ? LocalDateTime.now() : DateTimeParser.parseDateTime(end));
	}
}
