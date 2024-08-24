package team05.integrated_feed_backend;

import java.util.ArrayList;

import team05.integrated_feed_backend.common.enums.SocialMediaType;
import team05.integrated_feed_backend.module.post.entity.Post;

public class MockEntityFactory {

	// Post 생성
	public static Post createMockPost() {
		return Post.builder()
			.title("Sample Post Title")
			.content("This is a sample post content.")
			.type(SocialMediaType.FACEBOOK)
			.viewCount(100L)
			.likeCount(10L)
			.shareCount(5L)
			.postHashtags(new ArrayList<>())
			.build();
	}

}
