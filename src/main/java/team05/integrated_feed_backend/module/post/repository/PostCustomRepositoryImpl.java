package team05.integrated_feed_backend.module.post.repository;

import static team05.integrated_feed_backend.module.post.entity.QHashtag.*;
import static team05.integrated_feed_backend.module.post.entity.QPost.*;
import static team05.integrated_feed_backend.module.post.entity.QPostHashtag.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import team05.integrated_feed_backend.common.enums.OrderType;
import team05.integrated_feed_backend.common.enums.SocialMediaType;
import team05.integrated_feed_backend.module.post.dto.request.PostSearchReq;
import team05.integrated_feed_backend.module.post.entity.Post;
import team05.integrated_feed_backend.module.post.entity.QHashtag;
import team05.integrated_feed_backend.module.post.entity.QPost;
import team05.integrated_feed_backend.module.post.entity.QPostHashtag;

@Repository
@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<Post> searchPosts(PostSearchReq postSearchReq) {
		QPost post = QPost.post;
		QPostHashtag postHashtag = QPostHashtag.postHashtag;
		QHashtag hashtag = QHashtag.hashtag;

		return queryFactory
			.select(post)
			.distinct()
			.from(post)
			.leftJoin(post.postHashtags, postHashtag).fetchJoin()
			.leftJoin(postHashtag.hashtag, hashtag).fetchJoin()
			.where(
				typeEq(postSearchReq.getType()),
				searchByKeyword(postSearchReq.getSearchBy(), postSearchReq.getSearch()),
				hashtagEq(postSearchReq.getHashtag(), QPostHashtag.postHashtag)
			)
			.orderBy(getOrderSpecifier(postSearchReq))
			.offset((long)(postSearchReq.getPage() - 1) * postSearchReq.getLimit())
			.limit(postSearchReq.getLimit())
			.fetch();
	}

	private OrderSpecifier<?> getOrderSpecifier(PostSearchReq postSearchReq) {
		QPost post = QPost.post;
		boolean isAsc = postSearchReq.getOrder() == OrderType.ASC;

		switch (postSearchReq.getOrderBy()) {
			case "like_count":
				return isAsc ? post.likeCount.asc() : post.likeCount.desc();
			case "share_count":
				return isAsc ? post.shareCount.asc() : post.shareCount.desc();
			case "view_count":
				return isAsc ? post.viewCount.asc() : post.viewCount.desc();
			case "updated_at":
				return isAsc ? post.updatedAt.asc() : post.updatedAt.desc();
			case "created_at": // 기본값 처리
			default:
				return isAsc ? post.createdAt.asc() : post.createdAt.desc();
		}
	}

	@Override
	public Long countPosts(PostSearchReq postSearchReq) {
		QPost post = QPost.post;
		return queryFactory
			.select(post.count())
			.from(post)
			.leftJoin(post.postHashtags, postHashtag)
			.leftJoin(postHashtag.hashtag, hashtag)
			.where(
				typeEq(postSearchReq.getType()),
				searchByKeyword(postSearchReq.getSearchBy(), postSearchReq.getSearch()),
				hashtagEq(postSearchReq.getHashtag(), QPostHashtag.postHashtag)
			)
			.fetchOne();
	}

	private BooleanExpression hashtagEq(String hashtag, QPostHashtag postHashtag) {
		return (hashtag != null && postHashtag != null) ? postHashtag.hashtag.hashtagName.eq(hashtag) : null;
	}

	private BooleanExpression typeEq(SocialMediaType type) {
		return type != null ? QPost.post.type.eq(type) : null;
	}

	private BooleanExpression searchByKeyword(String searchBy, String keyword) {
		if (keyword == null || keyword.isEmpty()) {
			return null;
		}
		keyword = keyword.trim();
		if ("title".equals(searchBy)) {
			return post.title.contains(keyword);
		} else if ("content".equals(searchBy)) {
			return post.content.contains(keyword);
		} else if ("title,content".equals(searchBy)) {
			return post.title.contains(keyword).or(post.content.contains(keyword));
		}
		return null;
	}

}

