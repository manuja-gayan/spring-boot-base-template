package com.template.baseapp.domain.boundaries.service;

import com.template.baseapp.domain.dtos.BaseProductRequestDto;
import com.template.baseapp.domain.dtos.BaseProductResponseDto;
import com.template.baseapp.domain.exception.ProductCustomException;

import java.util.List;

public interface IManageProduct {
    BaseProductResponseDto createProduct(BaseProductRequestDto requestDto) throws ProductCustomException;
    List<BaseProductResponseDto> viewProducts();
    BaseProductResponseDto updateProduct(BaseProductRequestDto requestDto);
    Object deleteProduct(BaseProductRequestDto requestDto);
}
