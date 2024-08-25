package team05.integrated_feed_backend.module.post.repository;

import java.util.List;

import team05.integrated_feed_backend.module.post.dto.request.PostSearchReq;
import team05.integrated_feed_backend.module.post.entity.Post;

public interface PostCustomRepository {
	List<Post> searchPosts(PostSearchReq postSearchReq);

	Long countPosts(PostSearchReq postSearchReq);
}
