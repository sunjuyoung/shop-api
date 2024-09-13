package com.project.shop.product.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductModifyDTO {

    private Long productId;

    @NotBlank(message = "빈칸입니다")
    private String name;

    @NotBlank(message = "빈칸입니다")
    private String description;

    @NotNull(message = "빈칸입니다")
    @DecimalMin(value = "0.0")
    private BigDecimal price;

    @NotNull(message = "빈칸입니다")
    @Min(value = 0)
    private int stockQuantity;

    @NotNull(message = "빈칸입니다")
    private Long categoryId;

    @Builder.Default
    private List<MultipartFile> files = new ArrayList<>();

    @Builder.Default
    private List<String> uploadFileNames = new ArrayList<>();
}
