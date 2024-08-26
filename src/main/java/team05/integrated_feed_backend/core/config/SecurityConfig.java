package team05.integrated_feed_backend.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http
			.csrf(AbstractHttpConfigurer::disable) // CSRF 보호를 비활성화 (JWT를 사용하기 때문에)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 사용X
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/auth/**", "/swagger-ui/**", "/v3/api-docs/**")
				.permitAll() // 인증 없이 접근할 수 있는 경로 설정하기 (회원가입, 로그인 등)
				.requestMatchers(HttpMethod.POST, "/api/members")
				.permitAll()
				.anyRequest()
				.authenticated() // 그 외의 모든 요청 인증 필요
			);

		return http.build();
	}
}
