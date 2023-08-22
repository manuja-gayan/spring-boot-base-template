package com.template.baseapp.external.persistantmodel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "BaseProduct")
@Data
public class BaseProductJPAModel {
    @Id
    private long id;
    private String name;
    private double price;
    @Column(name = "created_at")
    private long createdAt;

    public BaseProductJPAModel(long id, String name, double price, long createdAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.createdAt = createdAt;
    }

    public BaseProductJPAModel() {
    }
}
