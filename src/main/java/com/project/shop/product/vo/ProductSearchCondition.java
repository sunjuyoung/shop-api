package com.project.shop.product.vo;

import com.project.shop.product.vo.enums.PriceRange;
import com.project.shop.product.vo.enums.SortDirection;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductSearchCondition {

    private String keyword;

    private String orderBy;

    private String direction;

    private List<Long> categoryId = new ArrayList<>();

    private PriceRange priceRange;
    private SortDirection sortDirection;


}
