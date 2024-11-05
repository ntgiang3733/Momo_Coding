package com.momo.controller;

import com.momo.dto.response.ResponseError;
import com.momo.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.momo.dto.response.ResponseData;
import com.momo.service.ProductService;

import java.util.List;


@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductService productService;

    @GetMapping("/all-products")
    public ResponseData<List<Product>> getAllProducts() {
        try {
            return new ResponseData<>(HttpStatus.OK.value(), "getAllProducts sc", productService.getAllProducts());
        } catch (Exception e) {
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
}
