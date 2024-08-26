package team05.integrated_feed_backend.module.post.dto.request;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team05.integrated_feed_backend.common.dto.PaginationQuery;
import team05.integrated_feed_backend.common.enums.SocialMediaType;

@Getter
@Setter
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class PostSearchReq extends PaginationQuery {

	@Schema(description = "해시태그")
	private String hashtag;

	@Schema(description = "SNS 타입", allowableValues = {"INSTAGRAM", "THREADS", "FACEBOOK", "TWITTER"})
	private SocialMediaType type;

	@Schema(description = "정렬 기준",
		allowableValues = {"created_at", "updated_at", "like_count", "share_count", "view_count"},
		defaultValue = "created_at"
	)
	@JsonProperty(defaultValue = "created_at")
	private String orderBy = "created_at";

	@Schema(description = "검색 기준",
		allowableValues = {"title", "content", "title,content"},
		defaultValue = "title,content"
	)
	@JsonProperty(defaultValue = "title,content")
	private String searchBy = "title,content";

	@Schema(description = "검색어")
	@Size(max = 20, message = "20글자보다 작아야 합니다.")
	private String search;

	public void setDefaultHashtagIfEmpty(String memberAccount) {
		if (hashtag == null || hashtag.isEmpty()) {
			this.hashtag = memberAccount;
		}
	}

}


