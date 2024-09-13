package com.project.shop.category.repository;

import com.project.shop.category.entity.Category;

import java.util.List;

public interface CustomCategoryRepository {

    List<Category> findAllCategories();
}
