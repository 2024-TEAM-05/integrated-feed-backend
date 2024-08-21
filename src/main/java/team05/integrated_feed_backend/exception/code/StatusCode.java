package team05.integrated_feed_backend.exception.code;

import org.springframework.http.HttpStatus;

public interface StatusCode {
	/** 정의한 code 이름 반환 **/
	String name();

	/** 정의한 code HttpStatus 반환 **/
	HttpStatus getHttpStatus();

	/** 정의한 code Message 반환 **/
	String getMessage();
}