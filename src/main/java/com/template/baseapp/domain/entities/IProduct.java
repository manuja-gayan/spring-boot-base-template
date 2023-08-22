package com.template.baseapp.domain.entities;

public interface IProduct {
    long getId();
    String getName();
    double getPrice();
    long getCreatedAt();
    void setId(long id);
    void setName(String name);
    void setPrice(double price);
    void setCreatedAt(long createdAt);
}
