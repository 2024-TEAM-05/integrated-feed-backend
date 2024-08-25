package team05.integrated_feed_backend.post;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import team05.integrated_feed_backend.module.post.event.LikeCountIncreaseEvent;
import team05.integrated_feed_backend.module.post.event.ShareCountIncreaseEvent;
import team05.integrated_feed_backend.module.post.event.publisher.PostEventPublisher;

@ExtendWith(MockitoExtension.class)
public class PostEventPublisherTest {

	@Mock
	private ApplicationEventPublisher eventPublisher;

	@InjectMocks
	private PostEventPublisher postEventPublisher;

	@Test
	@DisplayName("[성공] LikeCountIncreaseEvent가 비동기적으로 발행된다.")
	void shouldPublishLikeCountIncreaseEvent() {
		// Given
		Long postId = 1L;

		// when
		postEventPublisher.publishLikeCountIncreaseEvent(postId);

		// then
		// LikeCountIncreaseEvent 생성 및 호출 확인
		ArgumentCaptor<LikeCountIncreaseEvent> captor = ArgumentCaptor.forClass(LikeCountIncreaseEvent.class);
		verify(eventPublisher, times(1)).publishEvent(captor.capture());

		// 캡쳐된 이벤트의 존재 및 해당 postId를 갖고 있는지 확인
		LikeCountIncreaseEvent capturedEvent = captor.getValue();
		assertNotNull(capturedEvent);
		assertEquals(postId, capturedEvent.getPostId(), "Post ID should match");
	}

	@Test
	@DisplayName("[성공] ShareCountIncreaseEvent가 비동기적으로 발행된다.")
	void shouldPublishShareCountIncreaseEvent() {
		// Given
		Long postId = 1L;

		// when
		postEventPublisher.publishShareCountIncreaseEvent(postId);

		// then
		// LikeCountIncreaseEvent 생성 및 호출 확인
		ArgumentCaptor<ShareCountIncreaseEvent> captor = ArgumentCaptor.forClass(ShareCountIncreaseEvent.class);
		verify(eventPublisher, times(1)).publishEvent(captor.capture());

		// 캡쳐된 이벤트의 존재 및 해당 postId를 갖고 있는지 확인
		ShareCountIncreaseEvent capturedEvent = captor.getValue();
		assertNotNull(capturedEvent);
		assertEquals(postId, capturedEvent.getPostId(), "Post ID should match");
	}
}
