package com.project.shop.product.controller;

import com.project.shop.product.dto.request.ProductCreateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/products")
public class ProductAdminController {


    @PostMapping("/create")
    public void createProduct(ProductCreateDTO productCreateDTO) {
    }

}
