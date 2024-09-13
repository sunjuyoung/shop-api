package com.project.shop.security.handler;

import com.project.shop.global.exception.enums.ExceptionCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class LoginFailHandler implements AuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        log.info("--- LoginFailHandler --- ");
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        Map<String, Object> map = Map.of("status", HttpServletResponse.SC_UNAUTHORIZED,
                "code", ExceptionCode.LOGIN_FAILED.getCode(),
                "messageDetail", ExceptionCode.LOGIN_FAILED.getMessage(),
                "message", exception.getMessage());
        response.getWriter().println(map);

    }
}
