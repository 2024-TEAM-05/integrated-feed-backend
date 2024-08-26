package team05.integrated_feed_backend.module.post.repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private final QPostHashtag qPostHashtag = QPostHashtag.postHashtag;
	private final QHashtag qHashtag = QHashtag.hashtag;

	public List<PostStatisticsListRes> findPostStatisticsByQueryParameter(PostStatisticsListReq request) {
		DateTimeTemplate<String> dateOnly = Expressions.dateTimeTemplate(String.class, "TO_CHAR({0}, 'YYYY-MM-DD')",
			qPost.createdAt);

		JPAQuery<PostStatisticsListRes> query = jpaQueryFactory.select(
				Projections.fields(PostStatisticsListRes.class,
					dateOnly.as("date"),
					getCountField(request.getValue()).as("countByValue")
				))
			.from(qPost)
			.join(qPostHashtag).on(qPostHashtag.post.eq(qPost))
			.join(qHashtag).on(qPostHashtag.hashtag.eq(qHashtag))
			.where(qPost.createdAt.between(request.getStart(), request.getEnd()))
			.groupBy(dateOnly);

		// 해시태그가 있을 경우 필터링 --> 일단 디폴트 값을 안 받았으므로 필요한 부분
		if (request.getHashtag() != null && !request.getHashtag().isEmpty()) {
			query.where(qHashtag.hashtagName.eq(request.getHashtag()));
		}

		return query.fetch();
	}

	public List<PostStatisticsListRes> findPostStatisticsByQueryParameterWithHour(PostStatisticsListReq request) {
		DateTimeTemplate<String> dateTimeWithHours = Expressions.dateTimeTemplate(String.class,
			"TO_CHAR({0}, 'YYYY-MM-DD HH24:00')", qPost.createdAt);

		// 1. 조회 시작 ~ 끝 모든 시간대 저장
		List<LocalDateTime> timeSlots = createDateList(request.getStart(), request.getEnd());

		// 2. join을 통해 조건에 맞는 데이터 추출
		JPAQuery<PostStatisticsListRes> query = jpaQueryFactory.select(
				Projections.fields(PostStatisticsListRes.class,
					dateTimeWithHours.as("date"),
					getCountField(request.getValue()).as("countByValue")
				))
			.from(qPost)
			.join(qPostHashtag).on(qPostHashtag.post.eq(qPost))
			.join(qHashtag).on(qPostHashtag.hashtag.eq(qHashtag))
			.where(qPost.createdAt.between(request.getStart(), request.getEnd()))
			.groupBy(dateTimeWithHours);

		// 해시태그가 있을 경우 필터링 --> 일단 디폴트 값을 안 받았으므로 필요한 부분
		if (request.getHashtag() != null && !request.getHashtag().isEmpty()) {
			query.where(qHashtag.hashtagName.eq(request.getHashtag()));
		}

		// 3. 쿼리 fetch
		List<PostStatisticsListRes> results = query.fetch();

		// 4. map에 { "Date" : count } 형식으로 저장
		Map<String, Long> map = addResultToMap(results);

		// 5. 모든 시간대를 key로 만들어서 map 완성
		List<PostStatisticsListRes> finalResults = createPostStatisticsList(timeSlots, map);

		return finalResults;
	}

	/*
		timeSlots이라는 List 생성 후 조회 시작(00:00) ~ 끝(24:00) 날짜와 시간 1시간 간격으로 저장
	*/
	private List<LocalDateTime> createDateList(LocalDateTime start, LocalDateTime end) {
		List<LocalDateTime> timeSlots = new ArrayList<>();

		LocalDateTime current = start;
		while (!current.isAfter(end)) {
			timeSlots.add(current);
			current = current.plusHours(1);
		}
		return timeSlots;
	}

	/*
		1시간마다 모든 시간대를 보여줘야 하기 때문에 key값이 없는 map의 value 0으로 설정
	*/
	private List<PostStatisticsListRes> createPostStatisticsList(List<LocalDateTime> timeSlots, Map<String, Long> map) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		List<PostStatisticsListRes> statistics = new ArrayList<>();

		for (LocalDateTime timeSlot : timeSlots) {
			String formattedTime = timeSlot.format(formatter);
			Long count = map.getOrDefault(formattedTime, 0L);
			statistics.add(new PostStatisticsListRes(formattedTime, count));
		}
		return statistics;
	}

	/*
		map에 "2024-01-01 00:00" : 0 형식으로 저장
	*/
	private Map<String, Long> addResultToMap(List<PostStatisticsListRes> results) {
		Map<String, Long> map = new HashMap<>();
		for (PostStatisticsListRes result : results) {
			map.put(result.getDate(), result.getCountByValue());
		}
		return map;
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
