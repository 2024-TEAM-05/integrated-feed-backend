package team05.integrated_feed_backend.module.member.event;

import team05.integrated_feed_backend.module.member.entity.Member;

public record SignedUpEvent(
	Member member
) {
}
