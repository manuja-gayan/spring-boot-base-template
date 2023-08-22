package com.template.baseapp.application.controller;


import com.template.baseapp.domain.boundaries.service.IManageProduct;
import com.template.baseapp.domain.dtos.BaseProductRequestDto;
import com.template.baseapp.domain.dtos.BaseProductResponseDto;
import com.template.baseapp.domain.exception.ProductCustomException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${base-url.context}/product")
public class ProductController {
    IManageProduct manageProduct;

    public ProductController(IManageProduct manageProduct) {
        this.manageProduct = manageProduct;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody BaseProductRequestDto requestModel,HttpServletRequest httpServletRequest) throws ProductCustomException {
        BaseProductResponseDto response = this.manageProduct.createProduct(requestModel);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<Object> getAll(HttpServletRequest httpServletRequest) throws ProductCustomException {
        List<BaseProductResponseDto> response = this.manageProduct.viewProducts();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody BaseProductRequestDto requestModel, HttpServletRequest httpServletRequest) throws ProductCustomException {
        BaseProductResponseDto response = this.manageProduct.updateProduct(requestModel);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/delete")
    public ResponseEntity<Object> delete(@RequestBody BaseProductRequestDto requestModel,HttpServletRequest httpServletRequest) throws ProductCustomException {
        Object response = this.manageProduct.deleteProduct(requestModel);
        return ResponseEntity.ok(response);
    }
}
