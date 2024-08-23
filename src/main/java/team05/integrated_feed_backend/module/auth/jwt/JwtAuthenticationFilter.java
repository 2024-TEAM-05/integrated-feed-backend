package team05.integrated_feed_backend.module.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import team05.integrated_feed_backend.common.util.JwtUtil;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = jwtUtil.resolveToken(request);

        if (token != null && jwtUtil.validateToken(token)) {
            String account = jwtUtil.getAccount(token);

            // UserDetailsService를 통해 UserDetails를 로드
            var userDetails = userDetailsService.loadUserByUsername(account);
            
            // 토큰이 유효한 경우 Authentication 생성
            UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) jwtUtil.getAuthentication(token, userDetails);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // SecurityContext에 객체 설정
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 다음 필터로 요청 전달
            filterChain.doFilter(request, response);
        }
    }
}
