package team05.integrated_feed_backend.common;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import team05.integrated_feed_backend.exception.code.StatusCode;

/**
 *  code, status, message 기본 응답 형식
 **/
@Getter
public class BaseApiResponse {
	private int code;
	private HttpStatus status;
	private String message;

	public BaseApiResponse(HttpStatus status, String message) {
		this.code = status.value();
		this.status = status;
		this.message = message;
	}

	// 상태, 메세지 정의해주는 경우
	public static BaseApiResponse of(HttpStatus httpStatus, String message) {
		return new BaseApiResponse(httpStatus, message);
	}

	// 상태만 정의, 메세지는 디폴트 메세지 사용하는 경우
	public static BaseApiResponse of(HttpStatus httpStatus) {
		return of(httpStatus, HttpStatus.valueOf(httpStatus.value()).getReasonPhrase());
	}

	// 상태 코드를 통해 정의하는 경우
	public static BaseApiResponse of(StatusCode statusCode) {
		return of(statusCode.getHttpStatus(), statusCode.getMessage());
	}

}