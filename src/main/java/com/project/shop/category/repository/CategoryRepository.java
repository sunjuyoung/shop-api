package com.project.shop.category.repository;

import com.project.shop.category.entity.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> , CustomCategoryRepository{



    @EntityGraph(attributePaths = "parent")
    List<Category> findByParentIsNotNull();


}
