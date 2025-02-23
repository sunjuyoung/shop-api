package com.project.shop.product.repository;

import com.project.shop.category.entity.QCategory;
import com.project.shop.global.domain.QImages;
import com.project.shop.global.exception.ProductNotFoundException;
import com.project.shop.global.exception.enums.ExceptionCode;
import com.project.shop.product.dto.response.*;
import com.project.shop.product.entity.Product;
import com.project.shop.product.entity.QProduct;
import com.project.shop.product.vo.ProductSearchCondition;
import com.project.shop.product.vo.enums.PriceRange;
import com.querydsl.core.Tuple;
import com.querydsl.core.group.Group;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.project.shop.category.entity.QCategory.*;
import static com.project.shop.global.domain.QImages.*;
import static com.project.shop.product.entity.QProduct.*;

public class ProductRepositoryImpl implements CustomProductRepository{

    private final JPAQueryFactory queryFactory;

    public ProductRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<ProductListDTO> searchProductListPage(ProductSearchCondition condition, Pageable pageable) {

        List<Product> result = getProductListDTOJPAQuery(condition, pageable);

        //orderByPrice(condition, result);
        List<ProductListDTO> productListDTOList = result.stream().map(ProductListDTO::new)
                .collect(Collectors.toList());

        JPAQuery<Long> countQuery = queryFactory.select(product.count())
                .from(product)
                .where(
                        nameEq(condition.getKeyword()),
                        categoryEq(condition.getCategoryId()),
                        priceRange(condition.getPriceRange())
                );

        return PageableExecutionUtils.getPage(productListDTOList, pageable, countQuery::fetchOne);

       // return null;
    }

    @Override
    public ProductViewDTO getProductView(Long productId) {

        ProductViewDTO productViewDTO = queryFactory.select(new QProductViewDTO(
                        product.id,
                        product.name,
                        product.description,
                        product.price,
                        product.quantity,
                        product.viewCount,
                category.id,
                category.name
                ))
                .from(product)
                .join(product.category, category)
                .where(product.id.eq(productId))
                .fetchOne();
        if(productViewDTO == null){
            throw new ProductNotFoundException(ExceptionCode.NOT_FOUND_PRODUCT);
        }

        List<ProductImagesDTO> imagesDTOS = queryFactory.select(new QProductImagesDTO(
                        images.id,
                        images.fileName
                ))
                .from(product)
                .leftJoin(product.productImages, images)
                .where(product.id.eq(productId))
                .fetch();
        productViewDTO.setProductImages(imagesDTOS);


        return productViewDTO;
    }


    private  List<Product> getProductListDTOJPAQuery(ProductSearchCondition condition, Pageable pageable) {
        JPAQuery<Product> fetch = queryFactory
                .select(product)
                .from(product)
                .innerJoin(product.category, category).fetchJoin()
                .leftJoin(product.productImages, images).fetchJoin()
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .where(
                        nameEq(condition.getKeyword()),
                        categoryEq(condition.getCategoryId()),
                        priceRange(condition.getPriceRange())
                );
        orderByPrice(condition,fetch);
        return fetch.fetch();
    }


    @Override
    public List<ProductListDTO> mdProductList() {
        JPAQuery<Product> productJPAQuery = productList();
        productJPAQuery.where(product.mdRecommended.eq(true));
        List<Product> fetch = productJPAQuery.fetch();
        List<ProductListDTO> productListDTOList = fetch.stream().map(ProductListDTO::new)
                .collect(Collectors.toList());
        return productListDTOList;
    }

    private  JPAQuery<Product> productList() {
        return  queryFactory
                .select(product)
                .from(product)
                .innerJoin(product.category, category).fetchJoin()
                .leftJoin(product.productImages, images).fetchJoin()
                .limit(10)
                .offset(0);
    }

    @Override
    public List<ProductListDTO> trendProductList() {
        JPAQuery<Product> productJPAQuery = productList();
        productJPAQuery.orderBy(product.viewCount.desc());
        List<Product> fetch = productJPAQuery.fetch();
        List<ProductListDTO> productListDTOList = fetch.stream().map(ProductListDTO::new)
                .collect(Collectors.toList());
        return productListDTOList;
    }

    @Override
    public List<ProductListDTO> newProductList() {
        JPAQuery<Product> productJPAQuery = productList();
        productJPAQuery.orderBy(product.updatedAt.desc());
        List<Product> fetch = productJPAQuery.fetch();
        List<ProductListDTO> productListDTOList = fetch.stream().map(ProductListDTO::new)
                .collect(Collectors.toList());
        return productListDTOList;
    }



    private static void orderByPrice(ProductSearchCondition condition, JPAQuery<Product> query) {
        if (StringUtils.hasText(condition.getOrderBy())) {
            switch (condition.getOrderBy()) {
                case "PRICE_HIGH":
                    query.orderBy(product.price.desc());
                    break;
                case "PRICE_LOW":
                    query.orderBy(product.price.asc());
                    break;
                case "NEW":
                    query.orderBy(product.updatedAt.desc());
                    break;
                case "BASIC":
                    query.orderBy(product.id.asc());
                    break;
            }
        }
    }


    private BooleanExpression nameEq(String keyword) {
        return StringUtils.hasText(keyword) ? product.name.contains(keyword) : null;
    }
    private BooleanExpression priceGoe(Integer priceGoe) {
        return priceGoe != null ? product.price.goe(priceGoe) : null;
    }

    private BooleanExpression categoryEq(List<Long> categoryId){
        if(!categoryId.isEmpty()){
        return    category.id.in(categoryId);
        }
        return null;
    }

    private BooleanExpression priceRange(PriceRange range) {
        if (range == null) return null;
        switch (range) {
            case UNDER_10000:
                return product.price.lt(10000);
            case BETWEEN_10000_AND_20000:
                return product.price.between(10000, 19999);
            case BETWEEN_20000_AND_30000:
                return product.price.between(20000, 29999);
            default:
                return null;
        }
    }

}
