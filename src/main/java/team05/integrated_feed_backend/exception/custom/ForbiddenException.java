package team05.integrated_feed_backend.exception.custom;

import lombok.Getter;
import team05.integrated_feed_backend.common.code.StatusCode;

/**
 * 권한이 없는 곳에 접근하고자 하는 경우
 * ex) Http Status 403
 **/
@Getter
public class ForbiddenException extends BusinessException {

	public ForbiddenException(StatusCode statusCode) {
		super(statusCode);
	}

}
