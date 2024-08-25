package team05.integrated_feed_backend.module.post.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.DateTimeTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import team05.integrated_feed_backend.module.post.dto.request.PostStatisticsListReq;
import team05.integrated_feed_backend.module.post.dto.response.PostStatisticsListRes;
import team05.integrated_feed_backend.module.post.entity.QHashtag;
import team05.integrated_feed_backend.module.post.entity.QPost;
import team05.integrated_feed_backend.module.post.entity.QPostHashtag;

@Repository
@RequiredArgsConstructor
public class PostStatisticsCustomRepositoryImpl implements PostStatisticsCustomRepository {
	private final JPAQueryFactory jpaQueryFactory;
	private final QPost qPost = QPost.post;

	public List<PostStatisticsListRes> findPostStatisticsByQueryParameter(PostStatisticsListReq request) {
		QPostHashtag qPostHashtag = QPostHashtag.postHashtag;
		QHashtag qHashtag = QHashtag.hashtag1;
		DateTimeTemplate<String> dateOnly = Expressions.dateTimeTemplate(String.class, "TO_CHAR({0}, 'YYYY-MM-DD')",
			qPost.createdAt);
		JPAQuery<PostStatisticsListRes> query = jpaQueryFactory.select(
				Projections.fields(PostStatisticsListRes.class,
					dateOnly.as("date"),
					getCountField(request.getValue()).as("count")
				))
			.from(qPost)
			.join(qPostHashtag).on(qPostHashtag.post.eq(qPost))
			.join(qHashtag).on(qPostHashtag.hashtag.eq(qHashtag))
			.where(qPost.createdAt.between(request.getStart(), request.getEnd()))
			.groupBy(dateOnly)
			.orderBy(dateOnly.asc());

		// 해시태그가 있을 경우 필터링 --> 일단 디폴트 값을 안 받았으므로 필요한 부분
		if (request.getHashtag() != null && !request.getHashtag().isEmpty()) {
			query.where(qHashtag.hashtag.eq(request.getHashtag()));
		}

		return query.fetch();
	}

	private NumberExpression<Long> getCountField(String value) {
		switch (value) {
			case "like_count":
				return qPost.likeCount.sum();
			case "share_count":
				return qPost.shareCount.sum();
			case "view_count":
				return qPost.viewCount.sum();
			default:
				return qPost.postId.count();
		}
	}
}
