package team05.integrated_feed_backend.module.post.dto.request;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Getter;

@Getter
public class PostStatisticsListReq {
	private String type;
	private String hashtag;
	private String value;
	private LocalDateTime start;
	private LocalDateTime end;

	public PostStatisticsListReq(String type, String hashtag, String value, LocalDate start, LocalDate end) {
		this.type = type;
		this.hashtag = hashtag;
		this.value = value;
		this.start = (start != null ? start.atStartOfDay() : LocalDate.now().atStartOfDay().minusDays(7));
		this.end = (end != null ? end.atTime(LocalTime.MAX) : LocalDate.now().atTime(LocalTime.MAX));
	}
}
