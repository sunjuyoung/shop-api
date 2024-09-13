package com.project.shop.category.service;

import com.project.shop.category.dto.request.CategoryRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryServiceTest {
    
    @Autowired
    private CategoryService categoryService;
    
    @Test
    void createParentCategory() {

        CategoryRequestDTO categoryDTO = new CategoryRequestDTO();
        categoryDTO.setName("아우터");

        CategoryRequestDTO categoryDTO1 = new CategoryRequestDTO();
        categoryDTO1.setName("이너");

        //잡화
        CategoryRequestDTO categoryDTO2 = new CategoryRequestDTO();
        categoryDTO2.setName("잡화");

        //하의
        CategoryRequestDTO categoryDTO3 = new CategoryRequestDTO();
        categoryDTO3.setName("하의");

        //정장
        CategoryRequestDTO categoryDTO4 = new CategoryRequestDTO();
        categoryDTO4.setName("정장");


        categoryService.createCategory(categoryDTO);
        categoryService.createCategory(categoryDTO1);
        categoryService.createCategory(categoryDTO2);
        categoryService.createCategory(categoryDTO3);
        categoryService.createCategory(categoryDTO4);

    }

    @Test
    void createChildCategory() {
        CategoryRequestDTO categoryDTO = new CategoryRequestDTO();
        categoryDTO.setName("코트");
        categoryDTO.setParentId(1L);

        //이너
        CategoryRequestDTO categoryDTO2 = new CategoryRequestDTO();
        categoryDTO2.setName("티셔츠");
        categoryDTO2.setParentId(2L);

        CategoryRequestDTO categoryDTO3 = new CategoryRequestDTO();
        categoryDTO3.setName("셔츠");
        categoryDTO3.setParentId(2L);

        CategoryRequestDTO categoryDTO4 = new CategoryRequestDTO();
        categoryDTO4.setName("맨투맨");
        categoryDTO4.setParentId(2L);

        CategoryRequestDTO categoryDTO5 = new CategoryRequestDTO();
        categoryDTO5.setName("니트");
        categoryDTO5.setParentId(2L);

        //가디건
        CategoryRequestDTO categoryDTO6 = new CategoryRequestDTO();
        categoryDTO6.setName("가디건");
        categoryDTO6.setParentId(2L);

        //잡화 하위 카테고리
        CategoryRequestDTO categoryDTO7 = new CategoryRequestDTO();
        categoryDTO7.setName("모자");
        categoryDTO7.setParentId(3L);

        CategoryRequestDTO categoryDTO8 = new CategoryRequestDTO();
        categoryDTO8.setName("가방");
        categoryDTO8.setParentId(3L);

        CategoryRequestDTO categoryDTO9 = new CategoryRequestDTO();
        categoryDTO9.setName("벨트");
        categoryDTO9.setParentId(3L);

        CategoryRequestDTO categoryDTO10 = new CategoryRequestDTO();
        categoryDTO10.setName("양말");
        categoryDTO10.setParentId(3L);

        //하의 하위 카테고리
        CategoryRequestDTO categoryDTO11 = new CategoryRequestDTO();
        categoryDTO11.setName("청바지");
        categoryDTO11.setParentId(4L);

        CategoryRequestDTO categoryDTO12 = new CategoryRequestDTO();
        categoryDTO12.setName("슬랙스");
        categoryDTO12.setParentId(4L);

        CategoryRequestDTO categoryDTO13 = new CategoryRequestDTO();
        categoryDTO13.setName("하프팬츠");
        categoryDTO13.setParentId(4L);


        categoryService.createCategory(categoryDTO);
        categoryService.createCategory(categoryDTO2);
        categoryService.createCategory(categoryDTO3);
        categoryService.createCategory(categoryDTO4);
        categoryService.createCategory(categoryDTO5);
        categoryService.createCategory(categoryDTO6);
        categoryService.createCategory(categoryDTO7);
        categoryService.createCategory(categoryDTO8);
        categoryService.createCategory(categoryDTO9);
        categoryService.createCategory(categoryDTO10);
        categoryService.createCategory(categoryDTO11);
        categoryService.createCategory(categoryDTO12);
        categoryService.createCategory(categoryDTO13);

    }

    @Test
    public void getAllCategory(){
        categoryService.getAllCategories();
    }

}