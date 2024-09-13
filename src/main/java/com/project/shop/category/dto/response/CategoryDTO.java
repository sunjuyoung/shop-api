package com.project.shop.category.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryDTO {
    private Long id;
    private String name;
    private List<CategoryDTO> subCategories;

    // Getters and Setters

    public CategoryDTO() {}

    public CategoryDTO(Long id, String name, List<CategoryDTO> subCategories) {
        this.id = id;
        this.name = name;
        this.subCategories = subCategories;
    }

}
