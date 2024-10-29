package com.project.shop.security.jwt;

import com.project.shop.global.exception.CustomJWTException;
import com.project.shop.global.exception.enums.ExceptionCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.sql.Date;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTProvider {
    private static String key = "034509324804235843098432144543676546435";
    private static final int TOKEN_LENGTH = 64;
    private static final SecureRandom secureRandom = new SecureRandom();
    private static  final Base64.Encoder base64Encoder = Base64.getUrlEncoder();


    private final RedisTemplate<String, String> redisTemplate;
    private static final long TOKEN_EXPIRATION_HOURS = 12L;
    private static final String REFRESH_TOKEN_PREFIX = "refreshToken:";


    // 토큰 생성
    public  String generateToken(Map<String,Object> valueMap, int min){
        SecretKey key = null;

        try {
            key = Keys.hmacShaKeyFor(JWTProvider.key.getBytes("UTF-8"));
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

        String jwtStr = Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setClaims(valueMap)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
                .signWith(key)
                .compact();

        return jwtStr;
    }

    public  Map<String,Object> validateToken(String token)   {
        Map<String,Object> claims = null;

        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(JWTProvider.key.getBytes("UTF-8")))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch(MalformedJwtException malformedJwtException){
            throw new CustomJWTException(ExceptionCode.MALFORM_TOKEN);
        }catch(ExpiredJwtException expiredJwtException){
            throw new CustomJWTException(ExceptionCode.EXPIRED_TOKEN);
        }catch(InvalidClaimException invalidClaimException){
            throw new CustomJWTException(ExceptionCode.INVALID_TOKEN);
        }catch(JwtException jwtException){
            throw new CustomJWTException(ExceptionCode.ERROR_TOKEN);
        }catch(UnsupportedEncodingException e){
            throw new RuntimeException(e.getMessage());
        }
        return claims;
    }

    public  String generateRefreshToken() {
        byte[] randomBytes = new byte[TOKEN_LENGTH];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);

    }

    public boolean refreshTokenSave(String refreshToken, String userId) {
        try {
            String key = REFRESH_TOKEN_PREFIX + userId;
            redisTemplate.opsForValue().set(
                    key,
                    refreshToken,
                    Duration.ofHours(TOKEN_EXPIRATION_HOURS));
            return true;
        } catch (Exception e) {
            log.error("Error saving refresh token to Redis :  {}", e.getMessage());
            return false;
        }
    }
}
