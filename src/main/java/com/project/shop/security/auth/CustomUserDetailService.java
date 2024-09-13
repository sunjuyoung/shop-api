package com.project.shop.security.auth;

import com.project.shop.customer.dto.AuthDTO;
import com.project.shop.customer.entity.Customer;
import com.project.shop.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found User"));

        AuthDTO authDTO = new AuthDTO(
                customer.getEmail(),
                customer.getPassword(),
                customer.getNickname(),
                customer.isSocial(),
                customer.getRoleList().stream().map(role -> role.name()).toList(),
                customer.getId()
        );

        return authDTO;
    }
}
