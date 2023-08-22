package com.template.baseapp.domain.service;

import com.template.baseapp.domain.boundaries.repository.IProductRepository;
import com.template.baseapp.domain.boundaries.service.IManageProduct;
import com.template.baseapp.domain.boundaries.service.IProductPresenter;
import com.template.baseapp.domain.dtos.BaseProductRequestDto;
import com.template.baseapp.domain.dtos.BaseProductResponseDto;
import com.template.baseapp.domain.entities.IProduct;
import com.template.baseapp.domain.entities.factories.IBaseProductFactory;
import com.template.baseapp.domain.exception.ProductCustomException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ManageProduct implements IManageProduct {

    IProductPresenter productPresenter;
    IProductRepository productRepository;
    IBaseProductFactory baseProductFactory;

    public ManageProduct(IProductPresenter productPresenter, IProductRepository productRepository, IBaseProductFactory baseProductFactory) {
        this.productPresenter = productPresenter;
        this.productRepository = productRepository;
        this.baseProductFactory = baseProductFactory;
    }

    @Override
    public BaseProductResponseDto createProduct(BaseProductRequestDto requestDto) throws ProductCustomException{
        if(productRepository.isExist(requestDto.getId())){
            return productPresenter.createFailedResponse(new ProductCustomException("Product already exist"));
        }

        IProduct product = baseProductFactory.create(requestDto.getId(), requestDto.getName(), requestDto.getPrice());
        product = productRepository.save(product);
        BaseProductResponseDto response = new BaseProductResponseDto(String.valueOf(product.getCreatedAt()), product.getId(), product.getName(), product.getPrice());
        return productPresenter.createSuccessResponse(response);
    }

    @Override
    public List<BaseProductResponseDto> viewProducts() {
        List<BaseProductResponseDto> response = new ArrayList<>();
        List<IProduct> products = productRepository.findAll();
        for (IProduct product : products) {
            BaseProductResponseDto baseProduct = new BaseProductResponseDto(String.valueOf(product.getCreatedAt()), product.getId(), product.getName(), product.getPrice());
            baseProduct = productPresenter.createSuccessResponse(baseProduct);
            response.add(baseProduct);
        }
        return response;
    }

    @Override
    public BaseProductResponseDto updateProduct(BaseProductRequestDto requestDto) {
        IProduct product = productRepository.findById(requestDto.getId());
        if(Objects.isNull(product)){
            return productPresenter.createFailedResponse(new ProductCustomException("Product not found"));
        }

        product.setId(requestDto.getId());
        product.setName(requestDto.getName());
        product.setPrice(requestDto.getPrice());
        product.setCreatedAt(Instant.now().toEpochMilli());
        productRepository.save(product);
        BaseProductResponseDto response = new BaseProductResponseDto(String.valueOf(product.getCreatedAt()), product.getId(), product.getName(), product.getPrice());
        return productPresenter.createSuccessResponse(response);
    }

    @Override
    public Object deleteProduct(BaseProductRequestDto requestDto) {
        IProduct product = productRepository.findById(requestDto.getId());
        if(Objects.isNull(product)){
            return productPresenter.createFailedResponse(new ProductCustomException("Product not found"));
        }
        productRepository.deleteProductById(requestDto.getId());
        return "Successfully deleted";
    }
}
