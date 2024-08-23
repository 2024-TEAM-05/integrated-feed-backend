package team05.integrated_feed_backend.module.auth.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import team05.integrated_feed_backend.exception.code.StatusCode;
import team05.integrated_feed_backend.module.member.entity.Member;
import team05.integrated_feed_backend.module.member.repository.MemberRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        Member member = memberRepository.findByAccount(account)
                .orElseThrow(() -> new UsernameNotFoundException(StatusCode.USER_NOT_FOUND.name()));

        return new CustomUserDetails(member);
    }
}
