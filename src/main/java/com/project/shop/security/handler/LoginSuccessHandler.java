package com.project.shop.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.shop.customer.dto.AuthDTO;
import com.project.shop.security.jwt.JWTProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private ObjectMapper objectMapper = new ObjectMapper();
    private final JWTProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        log.info("--- LoginSuccessHandler ---");
        AuthDTO authDTO =  (AuthDTO)authentication.getPrincipal();
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> claims = authDTO.getClaims();
        String id = claims.get("id").toString();
        String accessToken = jwtProvider.generateToken(claims, 60*12);
        String refreshToken = jwtProvider.generateToken(claims, 60*24);
        jwtProvider.refreshTokenSave(refreshToken,id);


        claims.put("refreshToken", refreshToken);
        claims.put("accessToken", accessToken);


        response.getWriter().write(objectMapper.writeValueAsString(claims));
    }


}
