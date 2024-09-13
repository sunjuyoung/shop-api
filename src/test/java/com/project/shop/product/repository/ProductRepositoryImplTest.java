package com.project.shop.product.repository;

import com.project.shop.product.entity.Product;
import com.project.shop.product.vo.ProductSearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryImplTest {

    @Autowired
    private ProductRepository productRepository;


    @Test
    void saveTest(){
        Product product = Product.builder()
                .name("Sample Product")
                .description("This is a sample product.")
                .price(new BigDecimal("99.99"))
                .discountRate(10)
                .quantity(100)
                .viewCount(0)
                .mdRecommended(true)
                .build();

      //  when(productRepository.save(any(Product.class))).thenReturn(product);

        // When
       // Product savedProduct = productService.saveProduct(product);

        // Then
//        assertEquals(product.getName(), savedProduct.getName());
//        assertEquals(product.getDescription(), savedProduct.getDescription());
//        assertEquals(product.getPrice(), savedProduct.getPrice());
//        assertEquals(product.getDiscountRate(), savedProduct.getDiscountRate());
//        assertEquals(product.getQuantity(), savedProduct.getQuantity());
//        assertEquals(product.getViewCount(), savedProduct.getViewCount());
//        assertEquals(product.isMdRecommended(), savedProduct.isMdRecommended());
    }

//    @Test
//    void searchProductListPage() {
//        // given
//        ProductSearchCondition condition = new ProductSearchCondition();
//        condition.setKeyword("test");
//        condition.setPriceGoe(1000);
//        condition.setPriceLoe(2000);
//
//        Pageable pageable = PageRequest.of(0, 10);
//
//        // when
//        productRepository.searchProductListPage(condition, pageable);
//
//        // then
//    }
//
//    @Test
//    void searchProductList() {
//        // given
//        ProductSearchCondition condition = new ProductSearchCondition();
//        condition.setKeyword("test");
//        condition.setPriceGoe(1000);
//
//        condition.setPriceLoe(2000);
//
//        Pageable pageable = PageRequest.of(0, 10);
//
//        // when
//
//        // then
//    }

}