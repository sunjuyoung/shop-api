package com.project.shop.security.oauth;

import com.project.shop.customer.dto.AuthDTO;
import com.project.shop.security.jwt.JWTProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SocialController {

    private final JWTProvider jwtProvider;
    private final SocialService socialService;


    @GetMapping("/auth/member/google")
    public Map<String, Object> getCustomerFromGoogle(@RequestParam("accessToken") String accessToken){
        AuthDTO authDTO = socialService.getGoogleMember(accessToken);
        Map<String, Object> claims = getClaims(authDTO);
        return claims;
    }


    @GetMapping("/auth/member/naver")
    public Map<String, Object> getCustomerFromNaver(@RequestParam("code") String code){
        AuthDTO authDTO = socialService.getNaverMember(code);
        Map<String, Object> claims = getClaims(authDTO);
        return claims;
    }


    private  Map<String, Object> getClaims(AuthDTO socialMember) {
        Map<String, Object> claims = socialMember.getClaims();

        String jwtAccessToken = jwtProvider.generateToken(claims, 10);
        String jwtRefreshToken = jwtProvider.generateToken(claims, 60 * 12);
        jwtProvider.refreshTokenSave(jwtRefreshToken,socialMember.getId().toString());
       // redisTemplate.opsForValue().set(googleMember.getEmail(), jwtRefreshToken, Duration.ofMinutes(60 * 12));

        claims.put("accessToken", jwtAccessToken);
        claims.put("refreshToken", jwtRefreshToken);
        return claims;
    }
}
