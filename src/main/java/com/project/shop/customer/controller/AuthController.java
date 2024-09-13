package com.project.shop.customer.controller;

import com.project.shop.customer.dto.request.SignUpDTO;
import com.project.shop.customer.dto.response.SignUpResponse;
import com.project.shop.customer.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpDTO signUpDTO){
        Long id = authService.signUp(signUpDTO);
        SignUpResponse response = new SignUpResponse(id, "success");
        return ResponseEntity.status(201).body(response);
    }


}
