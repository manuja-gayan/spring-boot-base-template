package com.template.baseapp.domain.entities.factories.impl;

import com.template.baseapp.domain.entities.BaseProduct;
import com.template.baseapp.domain.entities.IProduct;
import com.template.baseapp.domain.entities.factories.IBaseProductFactory;

import java.time.Instant;

public class BaseProductFactory implements IBaseProductFactory {
    @Override
    public IProduct create(long id, String name, double price) {
        return new BaseProduct(id,name,price, Instant.now().toEpochMilli());
    }
}
