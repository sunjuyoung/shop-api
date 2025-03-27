package com.project.shop.security.oauth;

import com.project.shop.customer.dto.AuthDTO;
import com.project.shop.customer.entity.Customer;
import com.project.shop.customer.entity.enums.Grade;
import com.project.shop.customer.entity.enums.Roles;
import com.project.shop.customer.repository.CustomerRepository;
import io.netty.channel.ChannelOption;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SocialService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${oauth.naver.client.id}")
    private String naverClientId;
    @Value("${oauth.naver.client.secret}")
    private String naverClientSecret;


    public AuthDTO getNaverMember(String code) {

        String accessToken = getAccessTokenByCode(code);
        String email = getEmailFromNaver(accessToken);
        AuthDTO authDTO = getMemberDTO(email);
        return authDTO;
    }


    private String getAccessTokenByCode(String authCode) {
        String tokenUrl = "https://nid.naver.com/oauth2.0/token";
        String client_id = naverClientId;
        String client_secret = naverClientSecret;
        String grant_type = "authorization_code";
        String code = authCode;



        WebClient webClient = WebClient.builder()
                .baseUrl("https://nid.naver.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create()
                                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000) // 5초 연결 타임아웃
                                .responseTimeout(Duration.ofSeconds(5)) // 5초 응답 타임아웃
                ))
                .build();

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/oauth2.0/token")
                        .queryParam("client_id", client_id)
                        .queryParam("client_secret", client_secret)
                        .queryParam("grant_type", grant_type)
                        .queryParam("code", authCode)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .map(response -> String.valueOf(response.get("access_token")))
                .block(); // 동기 처리

    }

    private String getEmailFromNaver(String accessToken) {
        String GetUserUrl = "https://openapi.naver.com/v1/nid/me";

        if(accessToken == null){
            throw new RuntimeException("Access Token is null");
        }

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+accessToken);
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<String>entity = new HttpEntity<>(headers);


        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(GetUserUrl).build();

        ResponseEntity<LinkedHashMap> response =
                restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET,entity,LinkedHashMap.class);
        //  log.info("response: "+response.getBody().get("response"));

        LinkedHashMap<String,String> userInfo = (LinkedHashMap<String, String>) response.getBody().get("response");

        String email = userInfo.get("email");

        return email;
    }

    private AuthDTO getMemberDTO(String email) {
        Optional<Customer> result = customerRepository.findByEmail(email);
        if(result.isPresent()){
            AuthDTO authDTO = entityToDTO(result.get());
            return authDTO;
        }
        //기존 회원이 아니라면
        Customer customer = makeSocialMember(email);
        customerRepository.save(customer);
        AuthDTO memberDTO = entityToDTO(customer);
        return memberDTO;
    }

    private AuthDTO entityToDTO(Customer customer){
        return new AuthDTO(
                customer.getEmail(),
                customer.getPassword(),
                customer.getNickname(),
                customer.isSocial(),
                customer.getRoleList().stream().map(roles -> roles.name()).toList(),
                customer.getId()
        );

    }

    private Customer makeSocialMember(String email){
        String tempPw = passwordEncoder.encode("temp1234");
        String nickname = email.split("@")[0];

        Customer customer =  Customer.builder()
                .email(email)
                .password(tempPw)
                .grade(Grade.BRONZE)
                .nickname(nickname)
                .isSocial(true)
                .build();

        customer.addRoles(Roles.CUSTOMER);
        return customer;
    }

    public AuthDTO getGoogleMember(String accessToken) {

        WebClient webClient = WebClient.builder()
                .baseUrl("https://www.googleapis.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create()
                                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000) // 5초 연결 타임아웃
                                .responseTimeout(Duration.ofSeconds(5)) // 5초 응답 타임아웃
                ))
                .build();

      return  webClient.get()
                // header 추가 하기  ("Authorization", "Bearer " + accessToken);

                .uri(uriBuilder -> uriBuilder
                        .path("/oauth2/v3/userinfo")
                        .queryParam("id_token", accessToken)
                        .build())
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                //log

                .map(response -> {
                    String email = String.valueOf(response.get("email"));
                    AuthDTO authDTO = getMemberDTO(email);
                    return authDTO;
                })
                .block(); // 동기 처리

    }
}
