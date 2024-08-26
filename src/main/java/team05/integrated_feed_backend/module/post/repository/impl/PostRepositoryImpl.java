package team05.integrated_feed_backend.module.post.repository.impl;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import team05.integrated_feed_backend.module.post.entity.Post;
import team05.integrated_feed_backend.module.post.entity.QHashtag;
import team05.integrated_feed_backend.module.post.entity.QPost;
import team05.integrated_feed_backend.module.post.entity.QPostHashtag;
import team05.integrated_feed_backend.module.post.repository.PostRepositoryCustom;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Optional<Post> findDetailPostById(Long id) {

		QPost post = QPost.post;
		QPostHashtag postHashtag = QPostHashtag.postHashtag;
		QHashtag hashtag = QHashtag.hashtag1;

		return Optional.ofNullable(
			jpaQueryFactory
				.selectFrom(post)
				.leftJoin(post.postHashtags, postHashtag).fetchJoin()
				.leftJoin(postHashtag.hashtag, hashtag).fetchJoin()
				.where(post.postId.eq(id))
				.fetchOne()
		);
		
	}
}
