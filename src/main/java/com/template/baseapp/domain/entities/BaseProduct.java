package com.template.baseapp.domain.entities;

public class BaseProduct implements IProduct{

    private long id;
    private String name;
    private double price;
    private long createdAt;

    public BaseProduct(long id, String name, double price, long createdAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.createdAt = createdAt;
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    @Override
    public long getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
    @Override
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public void setPrice(double price) {
        this.price = price;
    }
    @Override
    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
