package team05.integrated_feed_backend.module.post.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class PostSearchReq {

	@Schema(description = "해시태그")
	private String hashtag;

	@Schema(description = "SNS 타입")
	private String type;

	@Schema(description = "정렬 기준",
		allowableValues = {"created_at", "updated_at", "like_count", "share_count", "view_count"},
		defaultValue = "created_at"
	)
	@JsonProperty(value = "order_by", defaultValue = "created_at")
	private String orderBy = "created_at";

	@Schema(description = "검색 기준",
		allowableValues = {"title", "content", "title,content"},
		defaultValue = "title,content"
	)
	@JsonProperty(value = "search_by", defaultValue = "title,content")
	private String searchBy = "title,content";

	@Schema(description = "검색어")
	private String search;

}


