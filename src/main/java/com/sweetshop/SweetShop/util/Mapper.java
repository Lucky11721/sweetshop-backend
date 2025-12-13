package com.sweetshop.SweetShop.util;

import com.sweetshop.SweetShop.dto.SweetRequest;
import com.sweetshop.SweetShop.dto.SweetResponse;
import com.sweetshop.SweetShop.entity.Sweet;

public class Mapper {

    private Mapper() {
        // utility class
    }

    public static SweetResponse toSweetResponse(Sweet sweet) {
        return SweetResponse.builder()
                .id(sweet.getId())
                .name(sweet.getName())
                .category(sweet.getCategory())
                .price(sweet.getPrice())
                .quantity(sweet.getQuantity())
                .build();
    }

    public static Sweet toSweetEntity(SweetRequest request) {
        return Sweet.builder()
                .name(request.getName())
                .category(request.getCategory())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();
    }
}
