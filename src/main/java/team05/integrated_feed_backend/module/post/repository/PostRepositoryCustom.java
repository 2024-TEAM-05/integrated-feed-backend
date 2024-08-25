package team05.integrated_feed_backend.module.post.repository;

import team05.integrated_feed_backend.module.post.entity.Post;

public interface PostRepositoryCustom {

	Post findDetailPostById(Long id);
	
}
