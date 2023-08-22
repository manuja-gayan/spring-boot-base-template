package com.template.baseapp.application.config;

import com.template.baseapp.application.presenter.ProductPresenter;
import com.template.baseapp.domain.boundaries.repository.IProductRepository;
import com.template.baseapp.domain.boundaries.service.IManageProduct;
import com.template.baseapp.domain.boundaries.service.IProductPresenter;
import com.template.baseapp.domain.entities.factories.IBaseProductFactory;
import com.template.baseapp.domain.entities.factories.impl.BaseProductFactory;
import com.template.baseapp.domain.service.ManageProduct;
import com.template.baseapp.external.repository.impl.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public IProductPresenter productPresenter() {
        return new ProductPresenter();
    }

    @Bean
    public IBaseProductFactory baseProductFactory() {
        return new BaseProductFactory();
    }

    @Bean
    public IProductRepository productRepository() {
        return new ProductRepository();
    }

    @Bean
    public IManageProduct manageProduct(IProductPresenter productPresenter, IBaseProductFactory productFactory, IProductRepository productRepository) {
        return new ManageProduct(productPresenter, productRepository, productFactory);
    }
}
