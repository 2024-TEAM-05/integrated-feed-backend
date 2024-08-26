package team05.integrated_feed_backend.module.post.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import team05.integrated_feed_backend.module.post.entity.Post;

@Repository
public interface PostRepository
	extends JpaRepository<Post, Long>, PostCustomRepository, PostStatisticsCustomRepository {

	@Query("SELECT p FROM Post p " +
		"LEFT JOIN FETCH p.postHashtags ph " +
		"LEFT JOIN FETCH ph.hashtag h " +
		"WHERE p.id = :postId")
	Optional<Post> findDetailById(@Param("postId") Long postId);

}
