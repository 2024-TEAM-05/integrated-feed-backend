package team05.integrated_feed_backend.module.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import team05.integrated_feed_backend.module.auth.entity.VerificationCode;
import team05.integrated_feed_backend.module.member.entity.Member;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

	Optional<VerificationCode> findByMember(Member member);
}
