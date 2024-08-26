package team05.integrated_feed_backend.module.post.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import team05.integrated_feed_backend.common.code.StatusCode;
import team05.integrated_feed_backend.exception.custom.BusinessException;
import team05.integrated_feed_backend.module.post.dto.response.PostDetailRes;
import team05.integrated_feed_backend.module.post.entity.Post;
import team05.integrated_feed_backend.module.post.repository.PostRepository;

@ExtendWith(MockitoExtension.class)
class PostServiceGetPostDetailTest {

	@Mock
	private PostRepository postRepository;

	@InjectMocks
	private PostService postService;

	private Post mockPost;

	@BeforeEach
	void setUp() {
		mockPost = Post.builder()
			.postId(1L)
			.title("Sample Title")
			.content("Sample Content")
			.viewCount(100L)
			.likeCount(50L)
			.shareCount(10L)
			.build();
	}

	@Nested
	@DisplayName("게시물 상세 조회 테스트")
	class GetPostDetail {

		@Test
		@DisplayName("[성공] 게시물 상세 정보가 정상적으로 반환된다.")
		void getPostDetail_shouldReturnPostDetailRes_whenPostExists() {
			// Given
			when(postRepository.findDetailPostById(mockPost.getPostId())).thenReturn(Optional.of(mockPost));

			// When
			PostDetailRes postDetailRes = postService.getPostDetail(mockPost.getPostId());

			// Then
			assertNotNull(postDetailRes);
			assertEquals(mockPost.getPostId(), postDetailRes.getPost().getPostId());
			assertEquals(mockPost.getTitle(), postDetailRes.getPost().getTitle());
			assertEquals(mockPost.getContent(), postDetailRes.getPost().getContent());
			assertEquals(mockPost.getViewCount(), postDetailRes.getPost().getViewCount());
			assertEquals(mockPost.getLikeCount(), postDetailRes.getPost().getLikeCount());
			assertEquals(mockPost.getShareCount(), postDetailRes.getPost().getShareCount());

			// 레포지토리 메서드가 한 번 호출되었는지 확인
			verify(postRepository, times(1)).findDetailPostById(mockPost.getPostId());
		}

		@Test
		@DisplayName("[실패] 잘못된 ID 형식이 제공되면 400 Bad Request가 반환된다.")
		void getPostDetail_shouldThrowException_whenIdIsInvalid() {
			// Given
			Long invalidId = -1L;

			// When & Then
			BusinessException exception = assertThrows(BusinessException.class, () -> {
				postService.getPostDetail(invalidId);
			});

			assertEquals(StatusCode.BAD_REQUEST, exception.getStatusCode());
			assertEquals("잘못된 요청입니다.", exception.getMessage());
		}

		@Test
		@DisplayName("[실패] 게시물이 없을 때 예외를 던진다.")
		void getPostDetail_shouldThrowException_whenPostDoesNotExist() {
			// Given
			Long nonExistentPostId = 2L;
			when(postRepository.findDetailPostById(nonExistentPostId)).thenReturn(Optional.empty());

			// When & Then
			BusinessException exception = assertThrows(BusinessException.class, () -> {
				postService.getPostDetail(nonExistentPostId);
			});

			assertEquals(StatusCode.NOT_FOUND, exception.getStatusCode());
			assertEquals(StatusCode.NOT_FOUND.getMessage(), exception.getMessage());

			// 레포지토리 메서드가 한 번 호출되었는지 확인
			verify(postRepository, times(1)).findDetailPostById(nonExistentPostId);
		}

		@Test
		@DisplayName("[성공] 조회된 게시물의 view_count가 1 증가한다.")
		void getPostDetail_shouldIncreaseViewCount_whenPostExists() {
			// Given
			when(postRepository.findDetailPostById(mockPost.getPostId())).thenReturn(Optional.of(mockPost));

			// When
			postService.getPostDetail(mockPost.getPostId());

			// Then
			assertEquals(101L, mockPost.getViewCount());

			// 레포지토리 메서드가 한 번 호출되었는지 확인
			verify(postRepository, times(1)).findDetailPostById(mockPost.getPostId());
		}
	}
}