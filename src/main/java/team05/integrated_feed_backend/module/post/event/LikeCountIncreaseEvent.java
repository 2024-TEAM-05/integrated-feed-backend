package team05.integrated_feed_backend.module.post.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeCountIncreaseEvent {
	private final Long postId;
}
