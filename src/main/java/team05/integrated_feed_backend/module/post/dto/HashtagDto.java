package team05.integrated_feed_backend.module.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class HashtagDto {

	@Schema(description = "해시태그 id")
	@JsonProperty("hashtag_id")
	private Long hashtagId;

	@Schema(description = "해시태그")
	private String hashtag;

	public static HashtagDto of(Long hashtagId, String hashtag) {
		return HashtagDto.builder()
			.hashtagId(hashtagId)
			.hashtag("#" + hashtag)
			.build();
	}
}
