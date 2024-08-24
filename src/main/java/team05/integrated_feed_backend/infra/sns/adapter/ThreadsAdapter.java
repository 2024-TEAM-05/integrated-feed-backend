package team05.integrated_feed_backend.infra.sns.adapter;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team05.integrated_feed_backend.infra.sns.api.ThreadApi;

@Component
@RequiredArgsConstructor
@Slf4j
public class ThreadsAdapter {

	private final ThreadApi threadApi;

	public void increaseLikeCount(Long postId) {
		log.info("Increasing like count on Threads for post ID: {}", postId);
		threadApi.increaseLikeCount(postId);
	}

	public void increaseShareCount(Long postId) {
		log.info("Increasing share count on Threads for post ID: {}", postId);
		threadApi.increaseShareCount(postId);
	}
}
