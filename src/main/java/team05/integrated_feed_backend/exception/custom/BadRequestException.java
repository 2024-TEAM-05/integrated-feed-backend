package team05.integrated_feed_backend.exception.custom;

import lombok.Getter;
import team05.integrated_feed_backend.common.code.StatusCode;

/**
 * 요청이 잘못된 경우
 * ex) Http status 400
 **/
@Getter
public class BadRequestException extends BusinessException {

	public BadRequestException(StatusCode statusCode) {
		super(statusCode);
	}

}