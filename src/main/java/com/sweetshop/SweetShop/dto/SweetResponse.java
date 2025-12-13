package com.sweetshop.SweetShop.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SweetResponse {
    private Long id;
    private String name;
    private String category;
    private Double price;
    private Integer quantity;
    private String imageUrl;
}