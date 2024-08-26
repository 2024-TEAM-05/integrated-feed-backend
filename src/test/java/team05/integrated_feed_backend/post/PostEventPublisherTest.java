package team05.integrated_feed_backend.post;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import team05.integrated_feed_backend.common.enums.SocialMediaType;
import team05.integrated_feed_backend.module.post.event.LikeCountIncreasedEvent;
import team05.integrated_feed_backend.module.post.event.ShareCountIncreasedEvent;
import team05.integrated_feed_backend.module.post.event.publisher.PostEventPublisher;

@ExtendWith(MockitoExtension.class)
public class PostEventPublisherTest {

	@Mock
	private ApplicationEventPublisher eventPublisher;

	@InjectMocks
	private PostEventPublisher postEventPublisher;

	@Nested
	@DisplayName("LikeCountIncreasedEvent 비동기 발행")
	class LikeCountIncreasedEventPublisher {
		@Test
		@DisplayName("[성공] LikeCountIncreasedEvent가 비동기적으로 발행된다.")
		void shouldPublishLikeCountIncreasedEvent() {
			// Given
			Long postId = 1L;
			SocialMediaType type = SocialMediaType.FACEBOOK;

			// when
			postEventPublisher.publishLikeCountIncreasedEvent(postId, type);

			// then
			// LikeCountIncreasedEvent 생성 및 호출 확인
			ArgumentCaptor<LikeCountIncreasedEvent> captor = ArgumentCaptor.forClass(LikeCountIncreasedEvent.class);
			verify(eventPublisher, times(1)).publishEvent(captor.capture());

			// 캡쳐된 이벤트의 존재 및 해당 postId, SocialType 을 갖고 있는지 확인
			LikeCountIncreasedEvent capturedEvent = captor.getValue();
			assertNotNull(capturedEvent);
			assertEquals(postId, capturedEvent.getPostId(), "Post ID should match");
			assertEquals(type, capturedEvent.getType(), "Post Type should match");
		}
	}

	@Nested
	@DisplayName("ShareCountIncreasedEvent 비동기 발행")
	class ShareCountIncreasedEventPublisher {
		@Test
		@DisplayName("[성공] ShareCountIncreasedEvent가 비동기적으로 발행된다.")
		void shouldPublishShareCountIncreasedEvent() {
			// Given
			Long postId = 1L;
			SocialMediaType type = SocialMediaType.FACEBOOK;

			// when
			postEventPublisher.publishShareCountIncreasedEvent(postId, type);

			// then
			// ShareCountIncreasedEvent 생성 및 호출 확인
			ArgumentCaptor<ShareCountIncreasedEvent> captor = ArgumentCaptor.forClass(ShareCountIncreasedEvent.class);
			verify(eventPublisher, times(1)).publishEvent(captor.capture());

			// 캡쳐된 이벤트의 존재 및 해당 postId, SocialType 을 갖고 있는지 확인
			ShareCountIncreasedEvent capturedEvent = captor.getValue();
			assertNotNull(capturedEvent);
			assertEquals(postId, capturedEvent.getPostId(), "Post ID should match");
			assertEquals(type, capturedEvent.getType(), "Post Type should match");

		}
	}
}
