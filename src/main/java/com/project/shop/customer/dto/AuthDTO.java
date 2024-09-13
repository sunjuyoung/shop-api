package com.project.shop.customer.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class AuthDTO extends User {

    private String email;

    private String password;

    private String nickname;

    private boolean social;

    private List<String> roleNames = new ArrayList<>();

    private Long id;




    public AuthDTO(String email, String password, String nickname, boolean social, List<String> roleNames,Long id) {
        super(
                email,
                password,
                roleNames.stream().map(str -> new SimpleGrantedAuthority("ROLE_"+str)).collect(Collectors.toList()));

        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.social = social;
        this.roleNames = roleNames;
        this.id = id;
    }

    public Map<String, Object> getClaims() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("email", email);
        //dataMap.put("password",password);
        dataMap.put("nickname", nickname);
        dataMap.put("social", social);
        dataMap.put("roleNames", roleNames);
        dataMap.put("id",id);
        return dataMap;
    }
}
