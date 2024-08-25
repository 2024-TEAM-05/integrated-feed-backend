package team05.integrated_feed_backend.module.post.service;

import java.util.stream.Collectors;

import team05.integrated_feed_backend.module.post.dto.response.HashtagDto;
import team05.integrated_feed_backend.module.post.dto.response.PostDto;
import team05.integrated_feed_backend.module.post.entity.Post;
import team05.integrated_feed_backend.module.post.entity.PostHashtag;

public class PostMapper {

	public static PostDto toDto(Post post) {

		if (post == null) {
			return null;
		}

		return new PostDto(
			post.getPostId(),
			post.getTitle(),
			post.getContent(),
			post.getViewCount(),
			post.getLikeCount(),
			post.getShareCount(),
			post.getCreatedAt(),
			post.getUpdatedAt(),
			post.getPostHashtags().stream()
				.map(PostMapper::toDto)
				.collect(Collectors.toList())
		);

	}

	public static HashtagDto toDto(PostHashtag postHashtag) {

		if (postHashtag == null) {
			return null;
		}

		return new HashtagDto(
			postHashtag.getHashtag().getHashtagId(),
			postHashtag.getHashtag().getHashtag()
		);

	}
}
