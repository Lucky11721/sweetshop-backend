package com.sweetshop.SweetShop.service;

import com.sweetshop.SweetShop.dto.SweetRequest;
import com.sweetshop.SweetShop.dto.SweetResponse;

import java.util.List;

public interface SweetService {

    SweetResponse addSweet(SweetRequest sweetRequest);
    List<SweetResponse> getAllSweets();

    List<SweetResponse> searchSweets(
            String name,
            String category,
            Double minPrice,
            Double maxPrice
    );


    SweetResponse updateSweet(Long id, SweetRequest request);

    void deleteSweet(Long id);

    SweetResponse purchaseSweet(Long id, int quantity);

    SweetResponse restockSweet(Long id, int quantity);
}
