package team05.integrated_feed_backend.module.post.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShareCountIncreaseEvent {
	private final Long postId;
}