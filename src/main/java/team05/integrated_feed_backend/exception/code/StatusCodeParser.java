package team05.integrated_feed_backend.exception.code;

import org.springframework.stereotype.Component;

@Component
public class StatusCodeParser {

	// 예외 넘겨줄 때, 전송하는 StatusCode.getName을 받아서 파싱하는 메서드
	public StatusCode parse(String message) {
		String name = message;

		return GlobalStatusCode.valueOf(name);
	}
}