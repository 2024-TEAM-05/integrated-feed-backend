package team05.integrated_feed_backend.post;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import team05.integrated_feed_backend.common.enums.SocialMediaType;
import team05.integrated_feed_backend.infra.sns.adapter.FacebookAdapter;
import team05.integrated_feed_backend.infra.sns.adapter.InstagramAdapter;
import team05.integrated_feed_backend.infra.sns.adapter.TwitterAdapter;
import team05.integrated_feed_backend.module.post.event.LikeCountIncreasedEvent;
import team05.integrated_feed_backend.module.post.event.ShareCountIncreasedEvent;
import team05.integrated_feed_backend.module.post.event.listener.PostEventListener;

@ExtendWith(MockitoExtension.class)
public class PostEventListenerTest {

	@Mock
	private FacebookAdapter facebookAdapter;

	@Mock
	private TwitterAdapter twitterAdapter;

	@Mock
	private InstagramAdapter instagramAdapter;

	@InjectMocks
	private PostEventListener postEventListener;

	@Nested
	@DisplayName("게시물 좋아요 수 증가 이벤트 처리")
	class HandleLikeCountIncreasedEvent {

		@Test
		@DisplayName("[성공] LikeCountIncreasedEvent가 수신되어 Instagram API가 호출된다.")
		void shouldHandleLikeCountIncreasedEvent() {
			// Given
			Long postId = 1L;
			SocialMediaType type = SocialMediaType.INSTAGRAM;
			LikeCountIncreasedEvent event = new LikeCountIncreasedEvent(1L, type);

			// when
			postEventListener.handleLikeCountIncreasedEvent(event);

			// then
			verify(facebookAdapter, times(0)).increaseLikeCount(postId);
			verify(twitterAdapter, times(0)).increaseLikeCount(postId);
			verify(instagramAdapter, times(1)).increaseLikeCount(postId);
		}
	}

	@Nested
	@DisplayName("게시물 공유 수 증가 이벤트 처리")
	class HandleShareCountIncreasedEvent {

		@Test
		@DisplayName("[성공] ShareCountIncreasedEvent가 수신되어 Facebook API가 호출된다.")
		void shouldHandleShareCountIncreasedEvent() {
			// Given
			Long postId = 1L;
			SocialMediaType type = SocialMediaType.FACEBOOK;
			ShareCountIncreasedEvent event = new ShareCountIncreasedEvent(1L, type);

			// when
			postEventListener.handleShareCountIncreasedEvent(event);

			// then
			verify(facebookAdapter, times(1)).increaseShareCount(postId);
			verify(twitterAdapter, times(0)).increaseShareCount(postId);
			verify(instagramAdapter, times(0)).increaseShareCount(postId);
		}
	}
}