package team05.integrated_feed_backend.infra.sns.adapter;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team05.integrated_feed_backend.infra.sns.api.TwitterApi;

@Component
@RequiredArgsConstructor
@Slf4j
public class TwitterAdapter {

	private final TwitterApi twitterApi;

	public void increaseLikeCount(Long postId) {
		log.info("Increasing like count on Twitter for post ID: {}", postId);
		twitterApi.increaseLikeCount(postId);
	}

	public void increaseShareCount(Long postId) {
		log.info("Increasing share count on Twitter for post ID: {}", postId);
		twitterApi.increaseShareCount(postId);
	}
}

