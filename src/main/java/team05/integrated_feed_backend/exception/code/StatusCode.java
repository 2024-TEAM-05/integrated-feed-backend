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
	ILLEGAL_ARGUMENT(HttpStatus.BAD_REQUEST, "메서드에 잘못된 인자가 전달되었습니다."),
	RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 리소스를 찾을 수 없습니다."),
	METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "요청 경로가 지원되지 않습니다."),

	/**
	 * 500 번대 CODE
	 **/
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류가 발생했습니다."),
	UNEXPECT_INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예상치 못한 내부 서버 오류가 발생했습니다."),
	UNSUPPORTED_OPERATION(HttpStatus.NOT_IMPLEMENTED, "지원되지 않는 작업입니다.");

	private final HttpStatus httpStatus;
	private final String message;

	StatusCode(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}
}
