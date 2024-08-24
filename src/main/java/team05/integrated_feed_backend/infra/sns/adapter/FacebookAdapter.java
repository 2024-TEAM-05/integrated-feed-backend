package team05.integrated_feed_backend.infra.sns.adapter;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team05.integrated_feed_backend.infra.sns.api.FacebookApi;

@Component
@RequiredArgsConstructor
@Slf4j
public class FacebookAdapter {

	private final FacebookApi facebookApi;

	public void increaseLikeCount(Long postId) {
		log.info("Increasing like count on Facebook for post ID: {}", postId);
		facebookApi.increaseLikeCount(postId);
	}

	public void increaseShareCount(Long postId) {
		log.info("Increasing share count on Facebook for post ID: {}", postId);
		facebookApi.increaseShareCount(postId);
	}
}
