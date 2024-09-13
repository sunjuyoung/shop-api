package com.project.shop.customer.dto.response;

import com.project.shop.customer.entity.Customer;
import com.project.shop.customer.entity.enums.Grade;
import com.project.shop.customer.entity.enums.Roles;
import com.project.shop.customer.vo.Address;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CustomerResponse {

    private Long id;

    private String email;

    private String nickname;

    private String postCode;
    private String street;
    private String city;

    private String phoneNumber;

    private String grade;

    private boolean isSocial;

    private List<String> roleList = new ArrayList<>();

    private String profileImagePath;

    public void entityToDTO(Customer customer){
        this.id = customer.getId();
        this.email = customer.getEmail();
        this.nickname = customer.getNickname();
        if(customer.getAddress() != null){
            this.postCode = customer.getAddress().getPostCode();
            this.street = customer.getAddress().getStreet();
            this.city = customer.getAddress().getCity();
        }

        this.phoneNumber = customer.getPhoneNumber();
        this.grade = customer.getGrade().name();
        this.isSocial = customer.isSocial();
        this.profileImagePath = customer.getProfileImagePath();
        customer.getRoleList().stream().map(roles ->
             roleList.add(roles.name())
        );
    }
}
