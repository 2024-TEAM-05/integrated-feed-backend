package team05.integrated_feed_backend.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import team05.integrated_feed_backend.exception.custom.CustomAccessDeniedHandler;
import team05.integrated_feed_backend.exception.custom.CustomAuthenticationEntryPoint;
import team05.integrated_feed_backend.module.auth.jwt.JwtAuthenticationFilter;
import team05.integrated_feed_backend.module.auth.jwt.JwtManager;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final JwtManager jwtManager;
	private final UserDetailsService userDetailsService;
	private final CustomAccessDeniedHandler customAccessDeniedHandler;
	private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	private final PasswordEncoder passwordEncoder;

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter(jwtManager, userDetailsService);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http
			.csrf(csrf -> csrf.disable()) // CSRF 보호를 비활성화 (JWT를 사용하기 때문에)
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(
					"/swagger-ui/**",
					"/v3/api-docs/**",
					"/swagger-ui.html",
					"/api-docs/**",
					"/webjars/**"
				).permitAll()  // Swagger UI 관련 경로에 인증 없이 접근 허용
				.requestMatchers("/api/auth/**").permitAll() // 인증 없이 접근할 수 있는 경로 설정 (회원가입, 로그인 등)
				.requestMatchers(HttpMethod.POST, "/api/members").permitAll()
				.anyRequest().authenticated() // 그 외의 모든 요청 인증 필요
			)
			.exceptionHandling(exceptionHandling -> exceptionHandling
				.authenticationEntryPoint(customAuthenticationEntryPoint)  // 인증 실패 시 처리
			)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 사용 X
			.addFilterBefore(jwtAuthenticationFilter(),
				UsernamePasswordAuthenticationFilter.class); // JWT 필터를 UsernamePasswordAuthenticationFilter 앞에 추가

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authManagerBuilder
			.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder);
		return authManagerBuilder.build();
	}
}
