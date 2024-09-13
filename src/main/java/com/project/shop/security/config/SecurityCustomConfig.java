package com.project.shop.security.config;


import com.project.shop.security.filter.JWTCheckFilter;
import com.project.shop.security.handler.CustomAccessDeniedHandler;
import com.project.shop.security.handler.LoginFailHandler;
import com.project.shop.security.handler.LoginSuccessHandler;
import com.project.shop.security.jwt.JWTProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Log4j2
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityCustomConfig {


    private final JWTProvider jwtProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http.cors(httpSecurityCorsConfigurer -> {
            httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource());
        });

        //api서버는 무상태를 기본으로 사용하기 때문에 내부에서 세션을 생성하지 않도록 추가
        http.sessionManagement(sessionManagement -> {
            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });

        //기본은 get방식을 제외한 모든 요청에 csrf를 적용하도록 설정되어 있음
        //csrf를 사용하지 않도록 설정
        http.csrf(config -> {
            config.disable();
        });

//        http.authorizeHttpRequests((request)->
//                request
//                        .requestMatchers("/token/**","/auth/**", "/products/**", "/api/authenticate", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/v3/api-docs.yaml", "/api-edupay/json/**", "/favicon.ico").permitAll()
//
//
//
//        );
        http.formLogin(config->{
            config.usernameParameter("email");
            config.loginPage("/auth/login");
            config.successHandler(new LoginSuccessHandler(jwtProvider));
            config.failureHandler(new LoginFailHandler());
        });

        http.addFilterBefore(new JWTCheckFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling(config->{
            config.accessDeniedHandler(new CustomAccessDeniedHandler());
        });

        return http.build();
    }

    //
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOriginPatterns(Arrays.asList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS","HEAD"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization","Content-Type","Cache-Control"));
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfiguration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
