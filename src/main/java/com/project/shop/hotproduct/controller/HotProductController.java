package com.project.shop.hotproduct.controller;

import com.project.shop.hotproduct.service.HotProductService;
import com.project.shop.hotproduct.service.response.HotProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HotProductController {

    private final HotProductService hotProductService;

    @GetMapping("/hot-products/date/{dateStr}")
    public List<HotProductResponse> readAll(@PathVariable("dateStr") String dateStr){
        return hotProductService.readAll(dateStr);

    }
}
