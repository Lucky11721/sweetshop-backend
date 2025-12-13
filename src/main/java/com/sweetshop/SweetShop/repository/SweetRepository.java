package com.sweetshop.SweetShop.repository;

import com.sweetshop.SweetShop.entity.Sweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SweetRepository extends JpaRepository<Sweet, Long > {
    List<Sweet> findByNameContainingIgnoreCase(String name);

    List<Sweet> findByCategoryIgnoreCase(String category);

    List<Sweet> findByPriceBetween(Double minPrice, Double maxPrice);

}
