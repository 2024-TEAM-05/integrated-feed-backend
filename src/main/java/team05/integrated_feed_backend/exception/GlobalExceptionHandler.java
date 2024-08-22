package team05.integrated_feed_backend.exception;

import static org.springframework.http.HttpStatus.*;

import java.util.Arrays;
import java.util.MissingResourceException;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team05.integrated_feed_backend.common.BaseApiResponse;
import team05.integrated_feed_backend.exception.code.StatusCode;
import team05.integrated_feed_backend.exception.code.StatusCodeParser;
import team05.integrated_feed_backend.exception.custom.BusinessException;

@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

	private final StatusCodeParser statusCodeParser;

	/**
	 * 비즈니스 로직 상 발생할 수 있는 정의한 에러인 경우
	 * ex) 중복된 데이터 저장 요청, 찾을 수 없는 데이터 요청, 접근할 수 없는 데이터 요청, 잘못된 형식의 요청인 경우
	 * **/
	@ExceptionHandler({BusinessException.class})
	public ResponseEntity<BaseApiResponse> handleBusinessException(BusinessException e) {
		HttpStatus httpStatus = e.getStatusCode().getHttpStatus();

		// 서버 에러인 경우 stack trace
		if (httpStatus.value() == 500) {
			e.printStackTrace();
		}

		return ResponseEntity.status(httpStatus).body(BaseApiResponse.of(e.getStatusCode()));
	}

	/**
	 * 유효성 검사 실패 에러 잡는 경우
	 * ex) validation 으로 설정해둔 NotNull 필드가 null로 온 경우
	 * **/
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<BaseApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		// validation 검사 실패 항목 보여주는 메세지
		String errors = convertToErrorResponses(e);

		return ResponseEntity.badRequest().body(BaseApiResponse.of(BAD_REQUEST, errors));
	}

	/**
	 * 메서드 인자 타입이 맞지 않은 경우
	 * ex) Inteager type에 String type이 들어온 경우
	 * **/
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<BaseApiResponse> handleMethodArgumentTypeMismatchException(
		MethodArgumentTypeMismatchException e) {
		// 타입이 맞지 않은 메서드 인자 보여주는 메세지
		String message = formatMessageFrom(e);

		return ResponseEntity.badRequest().body(BaseApiResponse.of(BAD_REQUEST, message));
	}

	/**
	 * JSON 파싱 시, 잘못된 형식의 데이터를 넣은 경우
	 * ex) enumType 에 해당하지 않은 항목이 들어온 경우
	 * **/
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<BaseApiResponse> handleInvalidFormatException(InvalidFormatException e) {
		log.warn(e.getMessage(), e);

		// enum type 의 항목 보여주는 메세지
		String message = formatMessageFrom(e);

		return ResponseEntity.badRequest().body(BaseApiResponse.of(BAD_REQUEST, message));
	}

	/**
	 * 메서드로 잘못된 인자가 전달된 경우 (애플리케이션 에러일 가능성이 높음)
	 * ex) null 값을 허용하지 않는 인자에 null 이 전달된 경우
	 * **/
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<BaseApiResponse> handleBadRequest(IllegalArgumentException e) {
		log.warn(e.getMessage(), e);

		StatusCode statusCode = statusCodeParser.parse(e.getMessage());
		if (statusCode == null) {
			statusCode = StatusCode.ILLEGAL_ARGUMENT;
		}

		return ResponseEntity.status(statusCode.getHttpStatus()).body(BaseApiResponse.of(statusCode));
	}

	/**
	 * 지원되지 않는 기능이나, 메서드를 요청한 경우
	 * ex) post 메서드를 지원하지 않는 엔드 포인트에 post 요청한 경우
	 * **/
	// ?QNA 이거 원래 handleUnauthorized 라고 정의되어 있었는데, 해당 예외 찾아보니 그 기능이 아닌 것 같은데,
	// 이렇게 설정하신 이유가 있을지 궁금합니다.
	@ExceptionHandler(UnsupportedOperationException.class)
	public ResponseEntity<BaseApiResponse> handleUnsupportedOperation(UnsupportedOperationException e) {
		log.warn(e.getMessage(), e);

		StatusCode statusCode = statusCodeParser.parse(e.getMessage());
		if (statusCode == null) {
			statusCode = StatusCode.UNSUPPORTED_OPERATION;
		}

		return ResponseEntity.status(statusCode.getHttpStatus()).body(BaseApiResponse.of(statusCode));
	}

	/**
	 * 특정 리소스를 찾지 못한 경우 (애플리케이션 에러일 가능성이 높음)
	 * ex) 특정 경로에 대한 리소스를 찾지 못한 경우
	 * **/
	@ExceptionHandler(MissingResourceException.class)
	public ResponseEntity<BaseApiResponse> handleNotFound(MissingResourceException e) {
		log.warn(e.getMessage(), e);

		StatusCode statusCode = statusCodeParser.parse(e.getMessage());
		if (statusCode == null) {
			statusCode = StatusCode.RESOURCE_NOT_FOUND;
		}

		return ResponseEntity.status(statusCode.getHttpStatus()).body(BaseApiResponse.of(statusCode));
	}

	/**
	 * 정의하지 못한 내부 서버 에러인 경우
	 * **/
	@ExceptionHandler(Exception.class)
	public ResponseEntity<BaseApiResponse> handleUnknownException(Exception e) {
		log.error(e.getMessage(), e);

		StatusCode statusCode = statusCodeParser.parse(e.getMessage());
		if (statusCode == null) {
			statusCode = StatusCode.INTERNAL_SERVER_ERROR;
		}

		return ResponseEntity.internalServerError().body(BaseApiResponse.of(statusCode));
	}

	/**
	 * 예상하지 못한 곳에서 난 서버 에러인 경우
	 */
	// ?QNA status code parsing 문제인 것 같은데,
	// 해당 에러를 발생시키는 게 맞을지, 정의해둔 기존 에러 코드로 내보내는 게 나을지 고민
	private ResponseEntity<BaseApiResponse> handleUnexpected(String message) {
		log.error("예상하지 못한 에러가 발생했습니다. {}", message);

		StatusCode statusCode = StatusCode.UNEXPECT_INTERNAL_ERROR;

		return ResponseEntity.internalServerError().body(BaseApiResponse.of(statusCode));
	}

	/**
	 * validation 검사 실패한 항목 에러 메세지 만드는 메서드
	 */
	private String convertToErrorResponses(MethodArgumentNotValidException e) {
		return e.getFieldErrors()
			.stream()
			.map(DefaultMessageSourceResolvable::getDefaultMessage)
			.collect(Collectors.joining(", "));
	}

	/**
	 * 메서드 인자에 맞지 않은 자료형인 경우 에러 메세지 만드는 메서드
	 **/
	private String formatMessageFrom(MethodArgumentTypeMismatchException e) {
		String parameterName = e.getParameter().getParameterName();

		return parameterName + "의 타입이 잘못되었습니다.";
	}

	/**
	 * enumType에 해당하는 항목이 없는 경우 에러 메세지 만드는 메서드
	 **/
	private String formatMessageFrom(InvalidFormatException e) {
		Class<?> targetType = e.getTargetType();
		String enumTypeName = targetType.getSimpleName();
		String validValues = Arrays.stream(targetType.getEnumConstants())
			.map(enumConstant -> ((Enum<?>)enumConstant).name())
			.collect(Collectors.joining(", "));

		return enumTypeName + "는 [" + validValues + "] 중 하나여야 합니다.";
	}

}
