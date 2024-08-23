package team05.integrated_feed_backend.exception.custom;

import lombok.Getter;
import team05.integrated_feed_backend.exception.code.StatusCode;

/**
 * 요청 결과가 없는 경우
 * ex) Http Status 404
 **/
@Getter
public class DataNotFoundException extends BusinessException {

	public DataNotFoundException(StatusCode statusCode) {
		super(statusCode);
	}

}