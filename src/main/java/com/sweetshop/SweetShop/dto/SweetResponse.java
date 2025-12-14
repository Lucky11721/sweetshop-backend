package com.sweetshop.SweetShop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SweetResponse {
    private Long id;
    private String name;
    private String category;
    private Double price;
    private Integer quantity;
    private String imageUrl;
}