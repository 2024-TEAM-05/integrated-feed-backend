package team05.integrated_feed_backend.module.post.event.listener;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team05.integrated_feed_backend.common.enums.SocialMediaType;
import team05.integrated_feed_backend.infra.sns.adapter.FacebookAdapter;
import team05.integrated_feed_backend.infra.sns.adapter.InstagramAdapter;
import team05.integrated_feed_backend.infra.sns.adapter.TwitterAdapter;
import team05.integrated_feed_backend.module.post.event.LikeCountIncreasedEvent;
import team05.integrated_feed_backend.module.post.event.ShareCountIncreasedEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostEventListener {

	private final FacebookAdapter facebookAdapter;
	private final TwitterAdapter twitterAdapter;
	private final InstagramAdapter instagramAdapter;

	@Async
	@EventListener
	public void handleLikeCountIncreasedEvent(LikeCountIncreasedEvent event) {
		Long postId = event.getPostId();
		SocialMediaType type = event.getType();
		log.info("Asynchronously handling like count increase event for post ID: {}", postId);

		// 외부 API 호출
		switch (type) {
			case FACEBOOK -> facebookAdapter.increaseLikeCount(postId);
			case INSTAGRAM -> instagramAdapter.increaseLikeCount(postId);
			case TWITTER -> twitterAdapter.increaseLikeCount(postId);
			default -> log.error(postId + " 게시물의 " + type + ": facebook, instagram, twitter 중 하나로 설정되어 있지 않습니다.");
		}
	}

	@Async
	@EventListener
	public void handleShareCountIncreasedEvent(ShareCountIncreasedEvent event) {
		Long postId = event.getPostId();
		SocialMediaType type = event.getType();
		log.info("Asynchronously handling share count increase event for post ID: {}", postId);

		// 외부 API 호출
		switch (type) {
			case FACEBOOK -> facebookAdapter.increaseShareCount(postId);
			case INSTAGRAM -> instagramAdapter.increaseShareCount(postId);
			case TWITTER -> twitterAdapter.increaseShareCount(postId);
			default -> log.error(postId + " 게시물의 " + type + ": facebook, instagram, twitter 중 하나로 설정되어 있지 않습니다.");
		}
	}
}