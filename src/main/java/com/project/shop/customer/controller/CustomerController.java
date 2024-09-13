package com.project.shop.customer.controller;

import com.project.shop.customer.dto.request.PasswordChangeDTO;
import com.project.shop.customer.dto.response.CustomerResponse;
import com.project.shop.customer.service.CustomerService;
import com.project.shop.global.exception.PasswordNotMatchException;
import com.project.shop.global.exception.enums.ExceptionCode;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.project.shop.global.exception.enums.ExceptionCode.NOT_MATCH_PASSWORD;


@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getProfile(@PathVariable("id")Long id){
        CustomerResponse profile = customerService.getProfile(id);
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/modify-password")
    public ResponseEntity<Map<String,Long>> modifyPassword(@Valid @RequestBody PasswordChangeDTO passwordChangeDTO){
        if(!passwordChangeDTO.getNewPassword().equals(passwordChangeDTO.getConfirmNewPassword())){
            throw new PasswordNotMatchException(NOT_MATCH_PASSWORD);
        }
        Long modifiedId = customerService.modifyPassword(passwordChangeDTO);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("result",modifiedId));
    }





}
