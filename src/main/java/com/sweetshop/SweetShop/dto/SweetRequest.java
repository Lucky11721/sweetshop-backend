package com.sweetshop.SweetShop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SweetRequest {
    private String name;
    private String category;
    private Double price;
    private Integer quantity;
    private String imageUrl;
}