package com.project.shop.product.service;

import com.project.shop.category.entity.Category;
import com.project.shop.category.repository.CategoryRepository;
import com.project.shop.global.domain.Images;
import com.project.shop.global.domain.ImagesRepository;
import com.project.shop.global.exception.NotFoundCategory;
import com.project.shop.global.exception.enums.ExceptionCode;
import com.project.shop.product.dto.request.ProductCreateDTO;
import com.project.shop.product.dto.request.ProductModifyDTO;
import com.project.shop.product.dto.response.ProductListDTO;
import com.project.shop.product.dto.response.ProductViewDTO;
import com.project.shop.product.entity.Product;
import com.project.shop.product.repository.ProductRepository;
import com.project.shop.product.vo.ProductSearchCondition;
import com.project.shop.util.CustomS3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductViewCountService productViewCountService;

    private final CategoryRepository categoryRepository;

    private final ImagesRepository imagesRepository;

    private final CustomS3Util customS3Util;

    public Page<ProductListDTO> searchProductListPage(ProductSearchCondition condition, Pageable pageable) {
        return productRepository.searchProductListPage(condition, pageable);
    }

    public ProductViewDTO getProductView(Long productId) {
        ProductViewDTO productView = productRepository.getProductView(productId);
        if(productView != null){
            productViewCountService.incrementViewCountAsync(productId);
        }
        return productView;
    }


    public Long modifyProduct(ProductModifyDTO modifyDTO){
        Product product = productRepository.findAllAndProductImagesById(modifyDTO.getProductId()).orElseThrow();
        List<Images> productImages = product.getProductImages();
        List<String> list = productImages.stream().map(images -> images.getFileName()).collect(Collectors.toList());
        customS3Util.deleteFiles(list);

        List<Long> ids = productImages.stream().map(im -> im.getId()).collect(Collectors.toList());

        imagesRepository.deleteAllByIdInBatch(ids);



        List<MultipartFile> files = modifyDTO.getFiles();
        List<String> strings = customS3Util.saveFiles(files);
        modifyDTO.setUploadFileNames(strings);
        Category category = categoryRepository.findById(modifyDTO.getCategoryId())
                .orElseThrow(() ->  new NotFoundCategory(ExceptionCode.NOT_FOUND_CATEGORY));

        product.setCategory(category);
        product.clearImage();
        product.modifyProduct(modifyDTO);

        for(String m : strings){
            product.addImageString(m);
        }

        return product.getId();
    }



    @Scheduled(fixedRate = 600000) //10분마다
    @Transactional
    public void updateViewCounts() {
        Map<Long, Long> viewCounts = productViewCountService.getAndResetViewCounts();
        if(viewCounts.isEmpty()){
            return;
        }
        for (Map.Entry<Long, Long> entry : viewCounts.entrySet()) {
            Long itemId = entry.getKey();
            Long count = entry.getValue();
            productRepository.incrementViewCountBatch(itemId, count);
        }
    }


    @Transactional
    public Long createProduct(ProductCreateDTO dto) {

        Product product = dtoToEntity(dto);
        Product save = productRepository.save(product);

        return save.getId();
    }

    private Product dtoToEntity(ProductCreateDTO productDTO){

        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() ->  new NotFoundCategory(ExceptionCode.NOT_FOUND_CATEGORY));



        Product product = Product.builder()
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .category(category)
                .quantity(productDTO.getStockQuantity())
                .build();

        //업로드 처리가 끝난 파일들의 이름 리스트
        List<String> uploadFileNames = productDTO.getUploadFileNames();

        if(uploadFileNames == null){
            return product;
        }

        uploadFileNames.stream().forEach(uploadName -> {
            product.addImageString(uploadName);
        });

        return product;
    }


    public List<ProductListDTO> getNewProductList() {
        return productRepository.newProductList();
    }

    @Cacheable(cacheNames = "getNewProductList", key = "'trendProduct'", cacheManager = "productListCacheManager")
    public List<ProductListDTO> getTrendProductList() {
        return  productRepository.trendProductList();
    }

    public List<ProductListDTO> getMdProductList() {
        return productRepository.mdProductList();
    }

    public void decrease(Product product,int quantity){
        product.minusStock(quantity);
    }
}
