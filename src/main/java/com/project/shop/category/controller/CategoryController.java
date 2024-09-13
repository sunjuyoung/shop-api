package com.project.shop.category.controller;

import com.project.shop.category.dto.response.CategoryDTO;
import com.project.shop.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getCategories(){
        List<CategoryDTO> allCategories = categoryService.getAllCategories();
        return ResponseEntity.ok(allCategories);
    }

    @GetMapping("/children")
    public ResponseEntity<List<CategoryDTO>> getChildrenCategories(){
        List<CategoryDTO> allCategories = categoryService.getChildrenCategories();
        return ResponseEntity.ok(allCategories);
    }
}
