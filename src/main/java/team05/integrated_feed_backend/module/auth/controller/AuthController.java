package team05.integrated_feed_backend.module.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import team05.integrated_feed_backend.module.auth.dto.JwtResponse;
import team05.integrated_feed_backend.module.auth.dto.LoginRequest;
import team05.integrated_feed_backend.module.auth.jwt.JwtManager;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtManager jwtManager;

	@PostMapping("/login")
	public JwtResponse login(@RequestBody LoginRequest loginRequest) {

		log.info("로그인 시도: {}", loginRequest.getAccount());

		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(
				loginRequest.getAccount(), loginRequest.getPassword()
			)
		);

		// 문제 없을 시 JWT 토큰 생성
		String token = jwtManager.generateToken(authentication);

		log.info("로그인 성공: {}", loginRequest.getAccount());
		return new JwtResponse(token);

		// 예외 처리 핸들러에 추가
	}
}
