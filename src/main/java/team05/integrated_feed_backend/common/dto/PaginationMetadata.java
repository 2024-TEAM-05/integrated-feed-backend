package team05.integrated_feed_backend.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
		int lastPage,
		Integer nextPage
	) {

		this.page = page;
		this.limit = limit;
		this.total = total;
		this.lastPage = lastPage;
		this.nextPage = nextPage;
	}

	public static PaginationMetadata of(int page, int limit, int total) {
		int lastPage = Math.max(1, (int)Math.ceil((double)total / limit));
		Integer nextPage = (page < lastPage) ? page + 1 : null;
		return new PaginationMetadata(page, limit, total, lastPage, nextPage);
	}

}
