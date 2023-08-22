package com.template.baseapp.external.repository;

import com.template.baseapp.external.persistantmodel.BaseProductJPAModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepositoryJPA extends JpaRepository<BaseProductJPAModel,Long> {
}
