package com.project.shop.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.project.shop.customer.dto.AuthDTO;
import com.project.shop.global.exception.CustomJWTException;
import com.project.shop.global.exception.enums.ExceptionCode;
import com.project.shop.global.exception.response.ErrorResponse;
import com.project.shop.security.jwt.JWTProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class JWTCheckFilter extends OncePerRequestFilter {


    private ObjectMapper objectMapper = new ObjectMapper();
    private final JWTProvider jwtProvider;
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();

        if(path.startsWith("/auth/")){
            return true;
        }

        if(path.startsWith("/token/")){
            return true;
        }

        if(path.startsWith("/products")){
            return true;
        }

        if(path.startsWith("/categories")){
            return true;
        }

        if(path.startsWith("/payments")){
            return true;
        }

        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        log.info("---------JWTCheckFilter doFilterInternal----------");


        try {

            String authHeaderStr =  request.getHeader("Authorization");

            if(authHeaderStr == null || !authHeaderStr.startsWith("Bearer ")){
                throw new CustomJWTException(ExceptionCode.NOT_FOUND_TOKEN);
            }
            String accessToken = authHeaderStr.substring(7);
            Map<String,Object> claims = jwtProvider.validateToken(accessToken);

            long id = (Integer) claims.get("id");
            String email = (String)claims.get("email");
            String nickname = (String)claims.get("nickname");
            Boolean social = (Boolean)claims.get("social");
            List<String> roles = (List<String>)claims.get("roleNames");

            AuthDTO authDTO = new AuthDTO(email,"",nickname,social.booleanValue(),roles,id);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(authDTO, "", authDTO.getAuthorities());


            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);


            filterChain.doFilter(request, response);
        }catch (CustomJWTException e){
            log.error("JWTCheckFilter error ");
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ErrorResponse errorResponse = ErrorResponse.of(e.getExceptionCode());
         //   response.getWriter().println(objectMapper.writeValueAsString(errorResponse));
            PrintWriter writer = response.getWriter();
            writer.println(objectMapper.writeValueAsString(errorResponse));
            writer.close();
        }



    }
}
