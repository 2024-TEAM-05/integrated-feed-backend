package team05.integrated_feed_backend.module.post.repository.impl;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import team05.integrated_feed_backend.module.post.entity.Post;
import team05.integrated_feed_backend.module.post.entity.QPost;
import team05.integrated_feed_backend.module.post.repository.PostRepositoryCustom;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Post findDetailPostById(Long id) {

		QPost post = QPost.post;
		return jpaQueryFactory
			.selectFrom(post)
			.where(post.postId.eq(id))
			.fetchOne();

	}
}
