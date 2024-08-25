package team05.integrated_feed_backend.module.post;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team05.integrated_feed_backend.common.dto.PaginationMetadata;
import team05.integrated_feed_backend.module.post.dto.request.PostSearchReq;
import team05.integrated_feed_backend.module.post.dto.response.PostSearchRes;
import team05.integrated_feed_backend.module.post.entity.Post;
import team05.integrated_feed_backend.module.post.repository.PostCustomRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

	private final PostCustomRepository postCustomRepository;

	public PostSearchRes getPosts(PostSearchReq req) {
		List<Post> posts = postCustomRepository.searchPosts(req);
		Long totalCount = postCustomRepository.countPosts(req);

		PaginationMetadata pageMetadata = PaginationMetadata.of(
			req.getPage(),
			req.getLimit(),
			totalCount
		);

		return PostSearchRes.of(posts, pageMetadata);
	}

}
