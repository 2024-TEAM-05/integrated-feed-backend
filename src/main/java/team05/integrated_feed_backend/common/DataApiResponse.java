package team05.integrated_feed_backend.common;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import team05.integrated_feed_backend.exception.code.StatusCode;

/**
 * 기본 응답형식에 data 가 추가된 data 응답 형식
 **/
@Getter
public class DataApiResponse<T> extends BaseApiResponse {
	private final T data;

	public DataApiResponse(T data, HttpStatus httpStatus, String message) {
		super(httpStatus, message);
		this.data = data;
	}

	// 상태, 메세지 정의해주는 경우
	public static <T> DataApiResponse<T> of(HttpStatus httpStatus, String message, T data) {
		return new DataApiResponse<>(data, httpStatus, message);
	}

	// 상태만 정의, 메세지는 디폴트 메세지 사용하는 경우
	public static <T> DataApiResponse<T> of(HttpStatus httpStatus, T data) {
		return of(httpStatus, HttpStatus.valueOf(httpStatus.value()).getReasonPhrase(), data);
	}

	// 상태 코드를 통해 정의하는 경우
	public static <T> DataApiResponse<T> of(StatusCode statusCode, T data) {
		return new DataApiResponse<>(data, statusCode.getHttpStatus(), statusCode.getMessage());
	}

}