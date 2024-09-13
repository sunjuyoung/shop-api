package com.project.shop.customer.repository;

import com.project.shop.customer.entity.Customer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {


    @EntityGraph(attributePaths = "roleList")
    Optional<Customer> findByEmail(String email);

    boolean existsByEmail(String email);

    @EntityGraph(attributePaths = "roleList")
    Optional<Customer> findById(Long id);


}
