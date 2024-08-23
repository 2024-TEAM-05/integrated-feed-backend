package team05.integrated_feed_backend.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeParser {
	public static LocalDateTime parseDateTime(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		if(date.length() == 10) date += "T00:00:00";
		return LocalDateTime.parse(date, formatter);
	}
}
