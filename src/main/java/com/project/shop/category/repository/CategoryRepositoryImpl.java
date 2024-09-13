package com.project.shop.category.repository;

import com.project.shop.category.entity.Category;
import com.project.shop.category.entity.QCategory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.project.shop.category.entity.QCategory.*;


public class CategoryRepositoryImpl implements CustomCategoryRepository{


    private final JPAQueryFactory queryFactory;

    public CategoryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Category> findAllCategories() {

        return queryFactory.selectFrom(category)
                .leftJoin(category.children).fetchJoin()
                .fetch();

    }
}
