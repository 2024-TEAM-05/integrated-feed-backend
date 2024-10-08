package team05.integrated_feed_backend.exception.custom;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import team05.integrated_feed_backend.common.BaseApiResponse;
import team05.integrated_feed_backend.common.code.StatusCode;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
		AccessDeniedException accessDeniedException) throws IOException {

		log.warn("CustomAccessDeniedHandler 호출");

		// StatusCode.FORBIDDEN을 사용하여 응답 생성
		StatusCode statusCode = StatusCode.FORBIDDEN;
		BaseApiResponse<Void> apiResponse = BaseApiResponse.of(statusCode);

		ResponseEntity<BaseApiResponse<Void>> entity = new ResponseEntity<>(apiResponse, statusCode.getHttpStatus());

		response.setStatus(entity.getStatusCodeValue());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(new ObjectMapper().writeValueAsString(entity.getBody()));
	}
}
