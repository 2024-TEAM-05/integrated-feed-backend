package team05.integrated_feed_backend.module.post.event.publisher;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team05.integrated_feed_backend.common.enums.SocialMediaType;
import team05.integrated_feed_backend.module.post.event.LikeCountIncreasedEvent;
import team05.integrated_feed_backend.module.post.event.ShareCountIncreasedEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostEventPublisher {

	private final ApplicationEventPublisher eventPublisher;

	@Async
	public void publishLikeCountIncreasedEvent(Long postId, SocialMediaType type) {
		eventPublisher.publishEvent(new LikeCountIncreasedEvent(postId, type));
		log.info("LikeCountIncreasedEvent published asynchronously for post ID: {}", postId);
	}

	@Async
	public void publishShareCountIncreasedEvent(Long postId, SocialMediaType type) {
		eventPublisher.publishEvent(new ShareCountIncreasedEvent(postId, type));
		log.info("ShareCountIncreasedEvent published asynchronously for post ID: {}", postId);
	}
}
