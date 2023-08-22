package com.template.baseapp.domain.boundaries.repository;

import com.template.baseapp.domain.entities.IProduct;

import java.util.List;

public interface IProductRepository {
    boolean isExist(long id);
    IProduct save(IProduct product);
    IProduct findById(long id);
    void deleteProductById(long id);
    List<IProduct> findAll();
}
