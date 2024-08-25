package team05.integrated_feed_backend.post;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import team05.integrated_feed_backend.infra.sns.adapter.FacebookAdapter;
import team05.integrated_feed_backend.infra.sns.adapter.InstagramAdapter;
import team05.integrated_feed_backend.infra.sns.adapter.TwitterAdapter;
import team05.integrated_feed_backend.module.post.event.LikeCountIncreaseEvent;
import team05.integrated_feed_backend.module.post.event.ShareCountIncreaseEvent;
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
	class HandleLikeCountIncreaseEvent {

		@Test
		@DisplayName("[성공] LikeCountIncreaseEvent가 수신되어 외부 API가 호출된다.")
		void shouldHandleLikeCountIncreaseEvent() {
			// Given
			Long postId = 1L;
			LikeCountIncreaseEvent event = new LikeCountIncreaseEvent(postId);

			// when
			postEventListener.handleLikeCountIncreaseEvent(event);

			// then
			verify(facebookAdapter, times(1)).increaseLikeCount(postId);
			verify(twitterAdapter, times(1)).increaseLikeCount(postId);
			verify(instagramAdapter, times(1)).increaseLikeCount(postId);
		}
	}

	@Nested
	@DisplayName("게시물 공유 수 증가 이벤트 처리")
	class HandleShareCountIncreaseEvent {

		@Test
		@DisplayName("[성공] ShareCountIncreaseEvent가 수신되어 외부 API가 호출된다.")
		void shouldHandleShareCountIncreaseEvent() {
			// Given
			Long postId = 1L;
			ShareCountIncreaseEvent event = new ShareCountIncreaseEvent(postId);

			// when
			postEventListener.handleShareCountIncreaseEvent(event);

			// then
			verify(facebookAdapter, times(1)).increaseShareCount(postId);
			verify(twitterAdapter, times(1)).increaseShareCount(postId);
			verify(instagramAdapter, times(1)).increaseShareCount(postId);
		}
	}
}