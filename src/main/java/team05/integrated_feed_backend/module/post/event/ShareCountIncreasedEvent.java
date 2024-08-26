package team05.integrated_feed_backend.module.post.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team05.integrated_feed_backend.common.enums.SocialMediaType;

@Getter
@AllArgsConstructor
public class ShareCountIncreasedEvent {
	private final Long postId;
	private final SocialMediaType type;
}