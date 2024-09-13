package com.project.shop.security.oauth;

import com.project.shop.customer.dto.AuthDTO;
import com.project.shop.customer.entity.Customer;
import com.project.shop.customer.entity.enums.Roles;
import com.project.shop.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.LinkedHashMap;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SocialService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;



    public AuthDTO getNaverMember(String code) {

        String accessToken = getAccessTokenByCode(code);
        String email = getEmailFromNaver(accessToken);
        AuthDTO authDTO = getMemberDTO(email);
        return authDTO;
    }


    private String getAccessTokenByCode(String authCode) {
        String tokenUrl = "https://nid.naver.com/oauth2.0/token";
        String client_id = "eofeAB8OANaCohB5ikzw";
        String client_secret = "Xsiso7IR7s";
        String grant_type = "authorization_code";
        String code = authCode;



        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(tokenUrl)
                .queryParam("client_id",client_id)
                .queryParam("client_secret",client_secret)
                .queryParam("grant_type",grant_type)
                .queryParam("code",code)
                .build();

        ResponseEntity<LinkedHashMap> response =
                restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET,entity,LinkedHashMap.class);

        LinkedHashMap<String,LinkedHashMap> bodyMap = response.getBody();

        String accessToken = String.valueOf(bodyMap.get("access_token"));

        return accessToken;

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
        Customer member = makeSocialMember(email);
        customerRepository.save(member);
        AuthDTO memberDTO = entityToDTO(member);
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
                .nickname(nickname)
                .isSocial(true)
                .build();

        customer.addRoles(Roles.CUSTOMER);
        return customer;
    }
}
