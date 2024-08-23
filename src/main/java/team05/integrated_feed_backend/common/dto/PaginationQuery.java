package team05.integrated_feed_backend.common.dto;

import org.springframework.validation.annotation.Validated;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Validated
public class PaginationQuery {

	@Schema(description = "Pagination - Page", defaultValue = "1")
	@Min(value = 1, message = "Page number must be at least 1")
	private int page = 1;

	@Schema(description = "Pagination - Limit", defaultValue = "10")
	@Min(value = 1, message = "Limit must be at least 1")
	private int limit = 10;

	@Schema(description = "정렬 기준", allowableValues = {"ASC", "DESC"})
	private OrderType order = OrderType.ASC;
}

	enum OrderType {
		ASC, DESC
	}