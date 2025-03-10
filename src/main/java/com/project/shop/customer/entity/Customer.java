package com.project.shop.customer.entity;

import com.project.shop.customer.entity.enums.Grade;
import com.project.shop.customer.entity.enums.Roles;
import com.project.shop.customer.vo.Address;
import com.project.shop.global.domain.BaseTime;
import com.project.shop.global.domain.Images;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "customer")
public class Customer extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Embedded
    private Address address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    private boolean isSocial;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private List<Roles> roleList = new ArrayList<>();

    private String profileImagePath;


    public static Customer createCustomer(String email, String password, String nickname, String phoneNumber, Grade grade, List<Roles> roleList){
        Customer customer = new Customer();
        customer.email = email;
        customer.password = password;
        customer.nickname = nickname;
        customer.phoneNumber = phoneNumber;
        customer.grade = grade;
        customer.roleList = roleList;
        return customer;
    }



    public void changeGrade(Grade grade){
        this.grade = grade;
    }

    public void addRoles(Roles role){
        roleList.add(role);
    }


    public void changePassword(String password){
        this.password = password;
    }

    public void changeSocial(boolean social) {
        this.isSocial = social;
    }

}
