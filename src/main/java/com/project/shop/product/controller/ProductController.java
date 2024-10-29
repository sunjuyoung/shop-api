package com.project.shop.product.controller;

import com.project.shop.product.dto.request.ProductCreateDTO;
import com.project.shop.product.dto.request.ProductModifyDTO;
import com.project.shop.product.dto.response.ProductListDTO;
import com.project.shop.product.dto.response.ProductViewDTO;
import com.project.shop.product.service.ProductService;
import com.project.shop.product.vo.ProductSearchCondition;
import com.project.shop.util.CustomS3Util;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {


    private final ProductService productService;
    private final CustomS3Util customS3Util;

    @GetMapping
    public ResponseEntity<Page<ProductListDTO>> getProductList(ProductSearchCondition condition, Pageable pageable) {
        Page<ProductListDTO> result = productService.searchProductListPage(condition, pageable);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/md")
    public ResponseEntity<List<ProductListDTO>> getMdProductList() {
        List<ProductListDTO> result = productService.getMdProductList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/new")
    public ResponseEntity<List<ProductListDTO>> getNewProductList() {
        List<ProductListDTO> result = productService.getNewProductList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/trend")
    public ResponseEntity<List<ProductListDTO>> getTrendProductList() {
        List<ProductListDTO> result = productService.getTrendProductList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductViewDTO> getProductView(@PathVariable("productId") Long productId) {
        ProductViewDTO productView = productService.getProductView(productId);
        return ResponseEntity.ok(productView);
    }

    @PostMapping
    public ResponseEntity<Long> register(ProductCreateDTO dto){
        List<MultipartFile> files = dto.getFiles();
        List<String> uploadFileNames = customS3Util.saveFiles(files);
        dto.setUploadFileNames(uploadFileNames);
        Long product = productService.createProduct(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductModifyResponse> modify(@PathVariable("productId")Long productId, ProductModifyDTO dto){
        dto.setProductId(productId);
        Long aLong = productService.modifyProduct(dto);


        return ResponseEntity.ok().body(new ProductModifyResponse("success",aLong));


    }


    public record ProductModifyResponse(String result,Long productId){}




}
