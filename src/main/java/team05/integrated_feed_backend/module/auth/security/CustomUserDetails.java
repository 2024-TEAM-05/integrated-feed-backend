package team05.integrated_feed_backend.module.auth.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import team05.integrated_feed_backend.module.member.entity.Member;

@Getter
public class CustomUserDetails implements UserDetails {

	private final Member member;

	public CustomUserDetails(Member member) {
		this.member = member;
	}

	@Override
	public String getUsername() {
		return member.getAccount();
	}

	@Override
	public String getPassword() {
		return member.getPassword();
	}

	// 계정 만료 여부 확인 로직
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// 계정 잠김 여부 확인 로직
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 자격 증명(비밀번호) 만료 여부 확인 로직
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return "verified".equals(member.getStatus());  // 인증된 상태만 활성화
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.emptyList();
	}

	public Long getMemberId() {
		return member.getMemberId();
	}

	public String getEmail() {
		return member.getEmail();
	}
}
