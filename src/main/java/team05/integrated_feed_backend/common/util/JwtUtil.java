package team05.integrated_feed_backend.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    // jwt.secret으로 설정된 값을 가져옴 : application.yml
    private String secretKey;

    @Value("${jwt.token-validity-in-seconds}")
    // JWT 토큰의 유효 시간 설정 : application.yml
    private long tokenValidityInseconds;

    // JWT 토큰 생성 (우선 Id, account 직접 받아오는 걸로 가정)
    public String generateToken(Long memberId, String account) {
        // JWT 토큰에 포함되는 정보(페이로드): jwt의 주체를 memberId로 설정
        Claims claims = Jwts.claims().setSubject(String.valueOf(memberId));
        claims.put("memberId", memberId); // memberId 클레임에 포함
        claims.put("account", account); // account를 추가적인 클레임으로 포함

        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenValidityInseconds * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)   // 발급 시간
                .setExpiration(validity)    // 만료 시간
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 서명
                .compact();
    }

    // JWT 토큰에서 memberId 추출 (Body: 페이로드)
    public Long getMemberId(String token) {
        return Long.parseLong(Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject());
    }

    // JWT 토큰에서 account 추출
    public String getAccount(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("account", String.class);
    }

    // JWT 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
