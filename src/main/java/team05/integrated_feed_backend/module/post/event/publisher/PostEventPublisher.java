package team05.integrated_feed_backend.module.post.event.publisher;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team05.integrated_feed_backend.module.post.event.LikeCountIncreaseEvent;
import team05.integrated_feed_backend.module.post.event.ShareCountIncreaseEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostEventPublisher {

	private final ApplicationEventPublisher eventPublisher;

	@Async
	public void publishLikeCountIncreaseEvent(Long postId) {
		eventPublisher.publishEvent(new LikeCountIncreaseEvent(postId));
		log.info("LikeCountIncreaseEvent published asynchronously for post ID: {}", postId);
	}

	@Async
	public void publishShareCountIncreaseEvent(Long postId) {
		eventPublisher.publishEvent(new ShareCountIncreaseEvent(postId));
		log.info("ShareCountIncreaseEvent published asynchronously for post ID: {}", postId);
	}
}
