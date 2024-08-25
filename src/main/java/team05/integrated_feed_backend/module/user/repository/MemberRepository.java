package team05.integrated_feed_backend.module.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import team05.integrated_feed_backend.module.user.entity.Member;
import team05.integrated_feed_backend.module.user.entity.vo.Email;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	boolean existsByAccount(String account);

	boolean existsByEmail(Email email);
}
