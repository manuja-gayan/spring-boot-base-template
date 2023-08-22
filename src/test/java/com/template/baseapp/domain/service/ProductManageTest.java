package com.template.baseapp.domain.service;

import com.template.baseapp.application.presenter.ProductPresenter;
import com.template.baseapp.domain.boundaries.repository.IProductRepository;
import com.template.baseapp.domain.boundaries.service.IProductPresenter;
import com.template.baseapp.domain.dtos.BaseProductRequestDto;
import com.template.baseapp.domain.entities.BaseProduct;
import com.template.baseapp.domain.entities.IProduct;
import com.template.baseapp.domain.entities.factories.IBaseProductFactory;
import com.template.baseapp.domain.exception.ProductCustomException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class ProductManageTest {
    IBaseProductFactory mockedFactory;

    IProductPresenter presenter;

    IProductRepository mockedRepository;

    ManageProduct manageProduct;

    BaseProductRequestDto request;

    @BeforeEach
    void setup(){
        mockedRepository = Mockito.mock(IProductRepository.class);
        mockedFactory = Mockito.mock(IBaseProductFactory.class);
        presenter = new ProductPresenter();
        manageProduct = new ManageProduct(presenter,mockedRepository,mockedFactory);
        request = new BaseProductRequestDto(1L,"name",10.0);
    }

    @Test
    void productCreateSuccessFlow(){
        when(mockedRepository.isExist(anyLong())).thenReturn(false);
        IProduct product = new BaseProduct(1L,"name",10.0, Instant.now().toEpochMilli());
        when(mockedRepository.save(any())).thenReturn(product);
        when(mockedFactory.create(anyLong(),anyString(),anyDouble())).thenReturn(product);
        Assertions.assertEquals(LocalDate.now().toString(), manageProduct.createProduct(request).getCreatedAt());
    }

    @Test
    void productGetProductListFlow(){
        IProduct product1 = new BaseProduct(1L,"name",10.0, Instant.now().toEpochMilli());
        IProduct product2 = new BaseProduct(2L,"name2",20.0, Instant.now().toEpochMilli());
        when(mockedRepository.findAll()).thenReturn(Arrays.asList(product1,product2));
        Assertions.assertEquals(2,manageProduct.viewProducts().size());
    }

    @Test
    void productCreateFailFlow(){
        when(mockedRepository.isExist(anyLong())).thenReturn(true);
        assertThrows(ProductCustomException.class, () -> manageProduct.createProduct(request));
    }

    @Test
    void productUpdateFailFlow(){
        when(mockedRepository.findById(anyLong())).thenReturn(null);
        assertThrows(ProductCustomException.class, () -> manageProduct.updateProduct(request));
    }

    @Test
    void productUpdateSuccessFlow(){
        IProduct product = new BaseProduct(1L,"name",10.0, Instant.now().toEpochMilli());
        when(mockedRepository.findById(anyLong())).thenReturn(product);
        when(mockedRepository.save(any())).thenReturn(product);
        Assertions.assertEquals(LocalDate.now().toString(), manageProduct.updateProduct(request).getCreatedAt());
    }

    @Test
    void productDeleteFailFlow(){
        when(mockedRepository.findById(anyLong())).thenReturn(null);
        assertThrows(ProductCustomException.class, () -> manageProduct.deleteProduct(request));
    }

    @Test
    void productDeleteSuccessFlow(){
        IProduct product = new BaseProduct(1L,"name",10.0, Instant.now().toEpochMilli());
        when(mockedRepository.findById(anyLong())).thenReturn(product);
        doNothing().when(mockedRepository).deleteProductById(anyLong());
        Assertions.assertEquals("Successfully deleted", manageProduct.deleteProduct(request));
    }
}
