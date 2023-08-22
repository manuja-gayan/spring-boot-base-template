package com.template.baseapp.domain.entities.factories;

import com.template.baseapp.domain.entities.IProduct;

public interface IBaseProductFactory {
    IProduct create(long id, String name, double price);
}
