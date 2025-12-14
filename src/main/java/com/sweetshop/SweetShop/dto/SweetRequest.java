package com.sweetshop.SweetShop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SweetRequest {
    private String name;
    private String category;
    private Double price;
    private Integer quantity;
    private String imageUrl;
}