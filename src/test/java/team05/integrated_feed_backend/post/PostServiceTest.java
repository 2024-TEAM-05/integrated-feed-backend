package team05.integrated_feed_backend.post;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import team05.integrated_feed_backend.MockEntityFactory;
import team05.integrated_feed_backend.exception.custom.DataNotFoundException;
import team05.integrated_feed_backend.module.post.entity.Post;
import team05.integrated_feed_backend.module.post.event.publisher.PostEventPublisher;
import team05.integrated_feed_backend.module.post.repository.PostRepository;
import team05.integrated_feed_backend.module.post.service.PostService;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

	@Mock
	private PostRepository postRepository;

	@Mock
	private PostEventPublisher postEventPublisher;

	@InjectMocks
	private PostService postService;

	private Post mockPost;

	@BeforeEach
	void setUp() {
		mockPost = MockEntityFactory.createMockPost();
	}

	@Nested
	@DisplayName("게시물 좋아요 수 올리기")
	class increaseLikeCount {

		@Test
		@DisplayName("[성공] 게시물 좋아요 수를 증가시켰다.")
		void shouldIncreaseLikeCountSuccessfully() {
			// Given
			when(postRepository.findById(mockPost.getPostId())).thenReturn(Optional.of(mockPost));

			// when
			postService.increaseLikeCount(mockPost.getPostId());

			// then
			verify(postRepository, times(1)).findById(mockPost.getPostId());
			verify(postEventPublisher, times(1)).publishLikeCountIncreasedEvent(mockPost.getPostId(),
				mockPost.getType());
			assert (mockPost.getLikeCount() == 11L);

		}

		@Test
		@DisplayName("[실패] 게시물이 없어 좋아요 수를 증가에 실패했다.")
		void shouldThrowExceptionWhenPostNotExists() {
			// Given
			when(postRepository.findById(mockPost.getPostId())).thenReturn(Optional.empty());

			// when & then
			assertThrows(DataNotFoundException.class, () -> {
				postService.increaseLikeCount(mockPost.getPostId());
			});

			verify(postRepository, times(1)).findById(mockPost.getPostId());
			verify(postEventPublisher, times(0)).publishLikeCountIncreasedEvent(mockPost.getPostId(),
				mockPost.getType());
		}
	}

	@Nested
	@DisplayName("게시물 공유 수 올리기")
	class increaseShareCount {

		@Test
		@DisplayName("[성공] 게시물 공유 수를 증가시켰다.")
		void shouldIncreaseShareCountSuccessfully() {
			// Given
			when(postRepository.findById(mockPost.getPostId())).thenReturn(Optional.of(mockPost));

			// when
			postService.increaseShareCount(mockPost.getPostId());

			// then
			verify(postRepository, times(1)).findById(mockPost.getPostId());
			verify(postEventPublisher, times(1)).publishShareCountIncreasedEvent(mockPost.getPostId(),
				mockPost.getType());
			assert (mockPost.getShareCount() == 6L);

		}
	}
}
