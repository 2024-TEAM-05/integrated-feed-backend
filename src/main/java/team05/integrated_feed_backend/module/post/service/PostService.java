package team05.integrated_feed_backend.module.post.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team05.integrated_feed_backend.common.code.StatusCode;
import team05.integrated_feed_backend.exception.custom.BusinessException;
import team05.integrated_feed_backend.module.post.dto.response.PostDetailRes;
import team05.integrated_feed_backend.module.post.entity.Post;
import team05.integrated_feed_backend.module.post.repository.PostRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

	private final PostRepository postRepository;

	@Transactional
	public PostDetailRes getPostDetail(Long id) {

		Post post = postRepository.findDetailPostById(id);

		if (post == null) {
			throw new BusinessException(StatusCode.NOT_FOUND, StatusCode.NOT_FOUND.getMessage());
		}

		post.incrementViewCount();

		return new PostDetailRes(PostMapper.toDto(post));

	}
}
