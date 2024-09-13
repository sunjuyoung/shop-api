package com.project.shop.category.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequestDTO {

    private String name;
    private Long parentId;
}
