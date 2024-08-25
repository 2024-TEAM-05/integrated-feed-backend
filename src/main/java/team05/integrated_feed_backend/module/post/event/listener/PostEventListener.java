package team05.integrated_feed_backend.module.post.event.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team05.integrated_feed_backend.infra.sns.adapter.FacebookAdapter;
import team05.integrated_feed_backend.infra.sns.adapter.InstagramAdapter;
import team05.integrated_feed_backend.infra.sns.adapter.TwitterAdapter;
import team05.integrated_feed_backend.module.post.event.LikeCountIncreaseEvent;
import team05.integrated_feed_backend.module.post.event.ShareCountIncreaseEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostEventListener {

	private final FacebookAdapter facebookAdapter;
	private final TwitterAdapter twitterAdapter;
	private final InstagramAdapter instagramAdapter;

	@EventListener
	public void handleLikeCountIncreaseEvent(LikeCountIncreaseEvent event) {
		Long postId = event.getPostId();
		log.info("Asynchronously handling like count increase event for post ID: {}", postId);

		// 외부 API 호출
		facebookAdapter.increaseLikeCount(postId);
		twitterAdapter.increaseLikeCount(postId);
		instagramAdapter.increaseLikeCount(postId);
	}

	@EventListener
	public void handleShareCountIncreaseEvent(ShareCountIncreaseEvent event) {
		Long postId = event.getPostId();
		log.info("Asynchronously handling share count increase event for post ID: {}", postId);

		// 외부 API 호출
		facebookAdapter.increaseShareCount(postId);
		twitterAdapter.increaseShareCount(postId);
		instagramAdapter.increaseShareCount(postId);
	}
}