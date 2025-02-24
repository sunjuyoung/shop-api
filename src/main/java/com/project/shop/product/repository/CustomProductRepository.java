package com.project.shop.product.repository;

import com.project.shop.product.dto.response.ProductListDTO;
import com.project.shop.product.dto.response.ProductViewDTO;
import com.project.shop.product.vo.ProductSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomProductRepository {

    public List<ProductListDTO> mdProductList();

    public List<ProductListDTO> trendProductList();

    public List<ProductListDTO> newProductList();

    public Page<ProductListDTO> searchProductListPage(ProductSearchCondition condition, Pageable pageable);
    public Page<ProductListDTO> searchProductListPagev2(ProductSearchCondition condition, Pageable pageable);



    public ProductViewDTO getProductView(Long productId);




}
