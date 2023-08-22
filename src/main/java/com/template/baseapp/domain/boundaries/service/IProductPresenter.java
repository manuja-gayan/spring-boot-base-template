package com.template.baseapp.domain.boundaries.service;

import com.template.baseapp.domain.dtos.BaseProductResponseDto;
import com.template.baseapp.domain.entities.BaseProduct;
import com.template.baseapp.domain.exception.ProductCustomException;

public interface IProductPresenter {

    BaseProductResponseDto createSuccessResponse(BaseProductResponseDto responseDto);
    BaseProductResponseDto createFailedResponse(ProductCustomException ex) throws ProductCustomException;
}
