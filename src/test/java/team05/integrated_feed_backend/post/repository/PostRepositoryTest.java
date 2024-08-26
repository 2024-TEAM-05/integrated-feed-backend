package team05.integrated_feed_backend.post.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import team05.integrated_feed_backend.common.enums.OrderType;
import team05.integrated_feed_backend.common.enums.SocialMediaType;
import team05.integrated_feed_backend.core.config.QueryDSLConfig;
import team05.integrated_feed_backend.module.post.dto.request.PostSearchReq;
import team05.integrated_feed_backend.module.post.entity.Hashtag;
import team05.integrated_feed_backend.module.post.entity.Post;
import team05.integrated_feed_backend.module.post.entity.PostHashtag;
import team05.integrated_feed_backend.module.post.repository.PostRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QueryDSLConfig.class)
@EnableJpaAuditing
public class PostRepositoryTest {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private TestEntityManager entityManager;

	@BeforeEach
	@DisplayName("테스트 데이터 설정")
	void setUp() {
		// Create Hashtags
		Hashtag hashtagSpring = Hashtag.builder()
			.hashtagName("Spring")
			.build();
		entityManager.persist(hashtagSpring);

		Hashtag hashtagJava = Hashtag.builder()
			.hashtagName("Java")
			.build();
		entityManager.persist(hashtagJava);

		// Create Posts
		Post post1 = Post.builder()
			.title("Spring Boot 게시물")
			.content("Spring Boot에 대한 내용입니다.")
			.type(SocialMediaType.INSTAGRAM)
			.viewCount(100L)
			.likeCount(10L)
			.shareCount(5L)
			.build();
		entityManager.persist(post1);

		Post post2 = Post.builder()
			.title("Java 개발 게시물")
			.content("Java 개발에 대한 내용입니다.")
			.type(SocialMediaType.TWITTER)
			.viewCount(200L)
			.likeCount(20L)
			.shareCount(10L)
			.build();
		entityManager.persist(post2);

		// Associate Posts with Hashtags using PostHashtag
		PostHashtag postHashtag1 = PostHashtag.builder()
			.post(post1)
			.hashtag(hashtagSpring)
			.build();
		post1.getPostHashtags().add(postHashtag1);
		entityManager.persist(postHashtag1);

		PostHashtag postHashtag2 = PostHashtag.builder()
			.post(post2)
			.hashtag(hashtagJava)
			.build();
		post2.getPostHashtags().add(postHashtag2);
		entityManager.persist(postHashtag2);

		entityManager.flush(); // Synchronize with the database
		entityManager.clear(); // Clear persistence context to avoid caching issues
	}

	@Test
	@DisplayName("제목으로 게시물 검색")
	void searchPostsByTitle() {
		// Given
		PostSearchReq req = new PostSearchReq();
		req.setSearch("Spring");
		req.setSearchBy("title");

		// When
		List<Post> result = postRepository.searchPosts(req);

		// Then
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getTitle()).isEqualTo("Spring Boot 게시물");
	}

	@Test
	@DisplayName("내용으로 게시물 검색")
	void searchPostsByContent() {
		// Given
		PostSearchReq req = new PostSearchReq();
		req.setSearch("개발");
		req.setSearchBy("content");

		// When
		List<Post> result = postRepository.searchPosts(req);

		// Then
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getContent()).contains("개발");
	}

	@Test
	@DisplayName("소셜 미디어 타입으로 게시물 검색")
	void searchPostsByType() {
		// Given
		PostSearchReq req = new PostSearchReq();
		req.setType(SocialMediaType.TWITTER);

		// When
		List<Post> result = postRepository.searchPosts(req);

		// Then
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getType()).isEqualTo(SocialMediaType.TWITTER);
	}

	@Test
	@DisplayName("제목과 내용으로 게시물 검색")
	void searchPostsByTitleAndContent() {
		// Given
		PostSearchReq req = new PostSearchReq();
		req.setSearch("개발"); // Search term that is found in both title and content
		req.setSearchBy("title,content"); // Search by both title and content

		// When
		List<Post> result = postRepository.searchPosts(req);

		// Then
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getTitle()).contains("개발");
		assertThat(result.get(0).getContent()).contains("개발");
	}

	@ParameterizedTest(name = "{0} 기준으로 게시물 정렬 테스트 - {1}")
	@MethodSource("provideSortingParameters")
	@DisplayName("정렬 기준에 따른 게시물 정렬")
	void searchPostsOrderBy(String orderBy, OrderType orderType) {
		// Given
		PostSearchReq req = new PostSearchReq();
		req.setOrderBy(orderBy);
		req.setOrder(orderType);

		// When
		List<Post> result = postRepository.searchPosts(req);

		// Then
		if (orderType == OrderType.DESC) {
			assertThat(result).isSortedAccordingTo((post1, post2) -> compareFields(post2, post1, orderBy));
		} else {
			assertThat(result).isSortedAccordingTo((post1, post2) -> compareFields(post1, post2, orderBy));
		}
	}

	private static int compareFields(Post post1, Post post2, String orderBy) {
		switch (orderBy) {
			case "created_at":
				return post1.getCreatedAt().compareTo(post2.getCreatedAt());
			case "updated_at":
				return post1.getUpdatedAt().compareTo(post2.getUpdatedAt());
			case "like_count":
				return post1.getLikeCount().compareTo(post2.getLikeCount());
			case "share_count":
				return post1.getShareCount().compareTo(post2.getShareCount());
			case "view_count":
				return post1.getViewCount().compareTo(post2.getViewCount());
			default:
				throw new IllegalArgumentException("Invalid orderBy field: " + orderBy);
		}
	}

	private static Stream<Arguments> provideSortingParameters() {
		return Stream.of(
			Arguments.of("created_at", OrderType.DESC),
			Arguments.of("created_at", OrderType.ASC),
			Arguments.of("updated_at", OrderType.DESC),
			Arguments.of("updated_at", OrderType.ASC),
			Arguments.of("like_count", OrderType.DESC),
			Arguments.of("like_count", OrderType.ASC),
			Arguments.of("share_count", OrderType.DESC),
			Arguments.of("share_count", OrderType.ASC),
			Arguments.of("view_count", OrderType.DESC),
			Arguments.of("view_count", OrderType.ASC)
		);
	}

	@Test
	@DisplayName("해시태그로 게시물 검색")
	void searchPostsByHashtag() {
		// Given
		PostSearchReq req = new PostSearchReq();
		req.setHashtag("Spring");

		// When
		List<Post> result = postRepository.searchPosts(req);

		// Then
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getTitle()).isEqualTo("Spring Boot 게시물");
	}

	@Test
	@DisplayName("페이징을 통한 첫 번째 게시물 검색")
	void searchPostsFirstPageWithPagination() {
		// Given
		PostSearchReq req = new PostSearchReq();
		req.setPage(1);  // First page
		req.setLimit(1); // Limit to 1 result per page

		// When
		List<Post> result = postRepository.searchPosts(req);

		// Then
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getTitle()).isEqualTo("Spring Boot 게시물");
	}

	@Test
	@DisplayName("페이징을 통한 게시물 검색 - 테스트 데이터가 리밋보다 적은 경우")
	void searchPostsWithLimitHigherThanData() {
		// Given
		PostSearchReq req = new PostSearchReq();
		req.setPage(1);
		req.setLimit(10);
		// When
		List<Post> result = postRepository.searchPosts(req);

		// Then
		assertThat(result).hasSize(2); // Expecting 2 posts because there are only 2 in the database
		assertThat(result.get(0).getTitle()).isEqualTo("Spring Boot 게시물");
		assertThat(result.get(1).getTitle()).isEqualTo("Java 개발 게시물");
	}
}
