package team05.integrated_feed_backend.module.post.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HashtagDto {

	@Schema(description = "해시태그 id")
	@JsonProperty("hashtag_id")
	private Long hashtagId;

	@Schema(description = "해시태그")
	private String hashtag;

}
