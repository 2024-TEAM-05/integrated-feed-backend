package team05.integrated_feed_backend.module.post.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team05.integrated_feed_backend.exception.code.StatusCode;
import team05.integrated_feed_backend.exception.custom.DataNotFoundException;
import team05.integrated_feed_backend.module.post.entity.Post;
import team05.integrated_feed_backend.module.post.event.publisher.PostEventPublisher;
import team05.integrated_feed_backend.module.post.repository.PostRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final PostEventPublisher postEventPublisher;

	@Transactional
	public void increaseLikeCount(Long postId) {
		// 게시물 존재 여부 확인
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new DataNotFoundException(StatusCode.POST_NOT_EXIST));

		// 내부 DB count 올리기
		post.increaseLikeCount();

		// 이벤트 비동기 발행
		postEventPublisher.publishLikeCountIncreasedEvent(postId, post.getType());

	}

	@Transactional
	public void increaseShareCount(Long postId) {
		// 게시물 존재 여부 확인
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new DataNotFoundException(StatusCode.POST_NOT_EXIST));

		// 내부 db count 올리기
		post.increaseShareCount();

		// 이벤트 비동기 발행
		postEventPublisher.publishShareCountIncreasedEvent(postId, post.getType());
	}
}
