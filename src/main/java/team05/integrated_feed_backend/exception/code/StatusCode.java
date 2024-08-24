package team05.integrated_feed_backend.exception.code;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum StatusCode {

	/**
	 * 200 번대 CODE
	 **/
	OK(HttpStatus.OK, "요청이 성공했습니다."),
	CREATED(HttpStatus.CREATED, "생성되었습니다."),

	/**
	 * 400 번대 CODE
	 **/
	METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "요청 경로가 지원되지 않습니다."),
	POST_NOT_EXIST(HttpStatus.NOT_FOUND, "존재하지 않는 게시물입니다."),

	/**
	 * 500 번대 CODE
	 **/
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류가 발생했습니다.");

	private final HttpStatus httpStatus;
	private final String message;

	StatusCode(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}

	/**
	 * 이름으로 StatusCode 찾고, 없다면 defaultStatusCode 로 정의하는 함수
	 **/
	public static StatusCode findStatusCodeByNameSafe(String name, StatusCode defaultStatusCode) {
		try {
			return StatusCode.valueOf(name);
		} catch (IllegalArgumentException e) {
			return defaultStatusCode;
		}
	}
}
