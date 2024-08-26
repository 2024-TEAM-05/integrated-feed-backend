package team05.integrated_feed_backend.module.post.repository;

import java.util.Optional;

import team05.integrated_feed_backend.module.post.entity.Post;

public interface PostRepositoryCustom {

	Optional<Post> findDetailPostById(Long id);

}
