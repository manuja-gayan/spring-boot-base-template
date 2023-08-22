package com.template.baseapp.domain.dtos;

public class BaseProductResponseDto extends BaseProductRequestDto{

    public BaseProductResponseDto(String createdAt, long id, String name, double price) {
        super(id,name,price);
        this.createdAt = createdAt;
    }

    private String createdAt;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
