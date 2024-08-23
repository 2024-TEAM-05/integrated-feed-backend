package team05.integrated_feed_backend.module.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team05.integrated_feed_backend.module.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByAccount(String account);
}

