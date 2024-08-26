package team05.integrated_feed_backend.module.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import team05.integrated_feed_backend.module.post.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostCustomRepository {

}
