package team05.integrated_feed_backend.common.util;

import team05.integrated_feed_backend.common.code.StatusCode;
import team05.integrated_feed_backend.exception.custom.BusinessException;

public class ValidationUtil {

	/**
	 * ID 요청값의 유효성 검사
	 * @param id 요청값
	 */
	public static void validateId(Long id) {
		if (id == null || id <= 0) {
			throw new BusinessException(StatusCode.BAD_REQUEST, StatusCode.BAD_REQUEST.getMessage());
		}
	}

}
