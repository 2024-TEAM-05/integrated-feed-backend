package team05.integrated_feed_backend.module.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import team05.integrated_feed_backend.module.member.entity.Member;
import team05.integrated_feed_backend.module.member.entity.vo.Email;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	boolean existsByAccount(String account);

	boolean existsByEmail(Email email);

	Optional<Member> findByAccount(String account);
}
