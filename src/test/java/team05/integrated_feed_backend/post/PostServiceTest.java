package team05.integrated_feed_backend.post;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
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
import team05.integrated_feed_backend.module.post.dto.request.PostSearchReq;
import team05.integrated_feed_backend.module.post.dto.response.PostSearchRes;
import team05.integrated_feed_backend.module.post.entity.Post;
import team05.integrated_feed_backend.module.post.event.publisher.PostEventPublisher;
import team05.integrated_feed_backend.module.post.repository.PostRepository;
import team05.integrated_feed_backend.module.post.service.PostService;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

	@Mock
	private PostRepository postRepository;

	@InjectMocks
	private PostService postService;

	private PostSearchReq postSearchReq;

	@Mock
	private PostEventPublisher postEventPublisher;

	private Post mockPost;

	@BeforeEach
	void setUp() {
		postSearchReq = new PostSearchReq();
		postSearchReq.setPage(1);
		postSearchReq.setLimit(10);
		mockPost = MockEntityFactory.createMockPost();
	}

	@Test
	@DisplayName("특정 검색어가 있을 때 게시물 목록 조회 성공")
	void getPosts_WithSearch_Success() {
		// Given
		String search = "Java";
		postSearchReq.setSearch(search);
		List<Post> postList = List.of(
			Post.builder()
				.title("Java 관련 게시물")
				.content("Java에 대한 내용을 다룹니다.")
				.build()
		);
		when(postRepository.searchPosts(postSearchReq)).thenReturn(postList);
		when(postRepository.countPosts(postSearchReq)).thenReturn((long)postList.size());

		// When
		PostSearchRes response = postService.getPosts(postSearchReq);

		// Then
		assertNotNull(response);
		assertNotNull(response.getPosts());
		assertNotNull(response.getMetadata());
		assertEquals(1, response.getPosts().size());
		verify(postRepository).searchPosts(postSearchReq);
		verify(postRepository).countPosts(postSearchReq);
	}

	@Test
	@DisplayName("검색어 없이 모든 게시물 목록 조회 성공")
	void getPosts_WithoutSearch_Success() {
		// Given
		List<Post> postList = List.of(
			Post.builder()
				.title("전체 게시물")
				.content("전체 게시물 내용을 다룹니다.")
				.build()
		);
		postSearchReq.setSearch(null); // 검색어 없음
		when(postRepository.searchPosts(postSearchReq)).thenReturn(postList);
		when(postRepository.countPosts(postSearchReq)).thenReturn((long)postList.size());

		// When
		PostSearchRes response = postService.getPosts(postSearchReq);

		// Then
		assertNotNull(response);
		assertNotNull(response.getPosts());
		assertNotNull(response.getMetadata());
		assertEquals(1, response.getPosts().size());
		verify(postRepository).searchPosts(postSearchReq);
		verify(postRepository).countPosts(postSearchReq);
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

