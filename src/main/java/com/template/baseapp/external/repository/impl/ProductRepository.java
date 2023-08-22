package com.template.baseapp.external.repository.impl;

import com.template.baseapp.domain.boundaries.repository.IProductRepository;
import com.template.baseapp.domain.entities.BaseProduct;
import com.template.baseapp.domain.entities.IProduct;
import com.template.baseapp.external.persistantmodel.BaseProductJPAModel;
import com.template.baseapp.external.repository.IProductRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepository implements IProductRepository {

    @Autowired
    IProductRepositoryJPA productRepositoryJPA;
    @Override
    public boolean isExist(long id) {
        return productRepositoryJPA.existsById(id);
    }

    @Override
    public IProduct save(IProduct product) {
        BaseProductJPAModel baseProductJPAModel = new BaseProductJPAModel(product.getId(), product.getName(), product.getPrice(), product.getCreatedAt());
        productRepositoryJPA.save(baseProductJPAModel);
        return product;
    }

    @Override
    public IProduct findById(long id) {
        Optional<BaseProductJPAModel> model = productRepositoryJPA.findById(id);
        return model.map(baseProductJPAModel -> new BaseProduct(baseProductJPAModel.getId(), baseProductJPAModel.getName(), baseProductJPAModel.getPrice(), baseProductJPAModel.getCreatedAt())).orElse(null);
    }

    @Override
    public void deleteProductById(long id) {
        productRepositoryJPA.deleteById(id);
    }

    @Override
    public List<IProduct> findAll() {
        List<IProduct> response = new ArrayList<>();
        List<BaseProductJPAModel> products = productRepositoryJPA.findAll();
        for (BaseProductJPAModel model : products) {
            IProduct iProduct = new BaseProduct(model.getId(), model.getName(), model.getPrice(), model.getCreatedAt());
            response.add(iProduct);
        }
        return response;
    }
}
