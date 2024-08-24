package team05.integrated_feed_backend.infra.sns.adapter;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team05.integrated_feed_backend.infra.sns.api.InstagramApi;

@Component
@RequiredArgsConstructor
@Slf4j
public class InstagramAdapter {

	private final InstagramApi instagramApi;

	public void increaseLikeCount(Long postId) {
		log.info("Increasing like count on Instagram for post ID: {}", postId);
		instagramApi.increaseLikeCount(postId);
	}

	public void increaseShareCount(Long postId) {
		log.info("Increasing share count on Instagram for post ID: {}", postId);
		instagramApi.increaseShareCount(postId);
	}
}
