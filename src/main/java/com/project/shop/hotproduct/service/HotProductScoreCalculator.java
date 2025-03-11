package com.project.shop.hotproduct.service;

import com.project.shop.hotproduct.repository.ProductCommentCountCache;
import com.project.shop.hotproduct.repository.ProductLikeCountCache;
import com.project.shop.hotproduct.repository.ProductViewedCountCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HotProductScoreCalculator {


    private final ProductViewedCountCache productViewCountRepository;
    private final ProductLikeCountCache productLikeCountRepository;
    private final ProductCommentCountCache productCommentCountRepository;



    private static final long PRODUCT_LIKE_COUNT_WEIGHT = 3;
    private static final long PRODUCT_COMMENT_COUNT_WEIGHT = 2;
    private static final long PRODUCT_VIEW_COUNT_WEIGHT = 1;

    public long calculate(Long productId) {
        Long likeCount = productLikeCountRepository.read(productId);
        Long viewCount = productViewCountRepository.read(productId);
        Long commentCount = productCommentCountRepository.read(productId);

        return likeCount * PRODUCT_LIKE_COUNT_WEIGHT
                + viewCount * PRODUCT_VIEW_COUNT_WEIGHT
                + commentCount * PRODUCT_COMMENT_COUNT_WEIGHT;
    }
}
