package com.project.shop.like.repository;

import com.project.shop.like.entity.ProductLike;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
class ProductLikeRepositoryTest {

    @MockBean
    ProductLikeRepository productLikeRepository;

    @Test
    void test(){
        Long productId = 11L;
        Long customerId = 2L;

        // When
        ProductLike productLike = productLikeRepository.findByUserIdAndProductId(customerId, productId).orElseThrow();

        // Then
        assertEquals(productLike.getProduct().getId(), productId);
    }

}