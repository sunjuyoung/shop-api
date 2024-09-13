package com.project.shop.category.service;

import com.project.shop.category.dto.request.CategoryRequestDTO;
import com.project.shop.category.dto.response.CategoryDTO;
import com.project.shop.category.entity.Category;
import com.project.shop.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public List<CategoryDTO> getChildrenCategories(){
        List<Category> categories = categoryRepository.findByParentIsNotNull();
        return categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

    }
    public void createCategory(CategoryRequestDTO categoryDTO) {
        Category category = Category.builder()
                .name(categoryDTO.getName())
                .build();
        if(categoryDTO.getParentId() != null){
            Category parent = categoryRepository.findById(categoryDTO.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid parent category id"));
            category.setParent(parent);
        }
        categoryRepository.save(category);
    }

    public List<CategoryDTO> getAllCategories(){
        List<Category> categories = categoryRepository.findAllCategories();
        return categories.stream()
                .filter(category -> category.getParent() == null)
                .map(this::convertToDTO)
                .collect(Collectors.toList());

    }

    private CategoryDTO convertToDTO(Category category) {
        List<CategoryDTO> subCategories = category.getChildren().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new CategoryDTO(category.getId(), category.getName(), subCategories);
    }
}
