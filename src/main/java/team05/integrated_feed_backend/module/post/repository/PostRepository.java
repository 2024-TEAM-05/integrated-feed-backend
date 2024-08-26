package team05.integrated_feed_backend.module.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import team05.integrated_feed_backend.module.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
