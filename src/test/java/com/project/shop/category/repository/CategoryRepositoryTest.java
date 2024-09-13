package com.project.shop.category.repository;

import com.project.shop.category.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryRepositoryTest {


    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void getAllCategories(){
        List<Category> allCategories = categoryRepository.findAllCategories();
        for (Category category : allCategories) {
            System.out.println("category = " + category);
        }
    }
}