package team05.integrated_feed_backend.infra.sns.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import team05.integrated_feed_backend.core.config.FeignConfig;

@FeignClient(name = "twitterApi", url = "https://www.twitter.com", configuration = {FeignConfig.class})
public interface TwitterApi {

	@PostMapping("/likes/{postId}")
	void increaseLikeCount(@PathVariable("postId") Long postId);

	@PostMapping("/share/{postId}")
	void increaseShareCount(@PathVariable("postId") Long postId);
}
