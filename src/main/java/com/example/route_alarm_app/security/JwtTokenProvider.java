package com.example.route_alarm_app.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.util.Base64; // Base64 인코딩/디코딩용 임포트 추가
import java.util.Date;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    private final SecretKey secretKey;
    private final long accessTokenExpirationMinutes;

    public JwtTokenProvider(
            @Value("${jwt.secret-key}") String secretKey,
            @Value("${jwt.access-token-expiration-minutes}") long accessTokenExpirationMinutes
    ) {
        byte[] decodedKey = Base64.getDecoder().decode(secretKey); // Base64 디코딩
        this.secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA256");
        this.accessTokenExpirationMinutes = accessTokenExpirationMinutes;
    }

    // Access Token 생성
    public String generateAccessToken(String subject, String role) {
        Instant now = Instant.now();
        Instant expiryDate = now.plusMillis(accessTokenExpirationMinutes * 60 * 1000);

        return Jwts.builder()
                .setSubject(subject)
                .claim("role", role)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiryDate))
                .signWith(secretKey, SignatureAlgorithm.HS256) // HS256 알고리즘 사용
                .compact();
    }

    // JWT 토큰 유효성 검증
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.", e);
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.", e);
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.", e);
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다: 문자열이 비어있거나 null", e);
        }
        return false;
    }

    // JWT 토큰에서 Claims (클레임) 추출
    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    // 토큰에서 사용자 ID (subject) 추출
    public String getSubject(String token) {
        return getClaimsFromToken(token).getSubject();
    }
}