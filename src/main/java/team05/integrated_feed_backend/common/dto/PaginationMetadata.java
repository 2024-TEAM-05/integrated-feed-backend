package team05.integrated_feed_backend.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PaginationMetadata {

	@JsonIgnore
	private final int page;

	@JsonIgnore
	private final int limit;

	@JsonIgnore
	private final int total;

	@JsonIgnore
	private final int lastPage;

	@JsonIgnore
	private final Integer nextPage;

	public PaginationMetadata(
		int page,
		int limit,
		int total,
		int lastPage
	) {

		this.page = page;
		this.limit = limit;
		this.total = total;
		this.lastPage = Math.max(1, (int) Math.ceil((double) total / limit));
		this.nextPage = (page < lastPage) ? page + 1 : null;

	}

	@JsonProperty("hasNext")
	@Schema(description = "Indicates if there is a next page.")
	public boolean hasNext() {
		return nextPage != null;
	}

	@JsonProperty("totalItems")
	@Schema(description = "The total number of items.")
	public int getTotal() {
		return total;
	}

}
