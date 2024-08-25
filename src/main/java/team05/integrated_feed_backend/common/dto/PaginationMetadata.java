package team05.integrated_feed_backend.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public record PaginationMetadata(@JsonIgnore int page, @JsonIgnore int limit, @JsonIgnore Long total,
								 @JsonIgnore int lastPage, @JsonIgnore Integer nextPage) {

	@JsonProperty("hasNext")
	@Schema(description = "Indicates if there is a next page.")
	public boolean hasNext() {
		return nextPage != null;
	}

	@Override
	@JsonProperty("totalItems")
	@Schema(description = "The total number of items.")
	public Long total() {
		return total;
	}

	public static PaginationMetadata of(int page, int limit, Long total) {
		int lastPage = Math.max(1, (int)Math.ceil((double)total / limit));
		Integer nextPage = (page < lastPage) ? page + 1 : null;
		return new PaginationMetadata(page, limit, total, lastPage, nextPage);
	}

}
