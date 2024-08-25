package team05.integrated_feed_backend.module.user.event;

import team05.integrated_feed_backend.module.user.entity.Member;

public record SignedUpEvent(
	Member member
) {
}
