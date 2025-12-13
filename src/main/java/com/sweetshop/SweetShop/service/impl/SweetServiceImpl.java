package com.sweetshop.SweetShop.service.impl;

import com.sweetshop.SweetShop.dto.SweetRequest;
import com.sweetshop.SweetShop.dto.SweetResponse;
import com.sweetshop.SweetShop.entity.Sweet;
import com.sweetshop.SweetShop.exception.BadRequestException; // Import this
import com.sweetshop.SweetShop.exception.ResourceNotFoundException; // Import this
import com.sweetshop.SweetShop.repository.SweetRepository;
import com.sweetshop.SweetShop.service.SweetService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SweetServiceImpl implements SweetService {
    private final SweetRepository sweetRepository;

    public SweetServiceImpl(SweetRepository sweetRepository) {
        this.sweetRepository = sweetRepository;
    }

    @Override
    public SweetResponse addSweet(SweetRequest request) {
        Sweet sweet = Sweet.builder()
                .name(request.getName())
                .category(request.getCategory())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .imageUrl(request.getImageUrl()) // 👈 Map the image URL
                .build();

        Sweet savedSweet = sweetRepository.save(sweet);
        return mapToResponse(savedSweet);
    }

    @Override
    public List<SweetResponse> getAllSweets() {
        return sweetRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<SweetResponse> searchSweets(
            String name,
            String category,
            Double minPrice,
            Double maxPrice
    ) {
        if (name != null) {
            return sweetRepository.findByNameContainingIgnoreCase(name)
                    .stream().map(this::mapToResponse).toList();
        }

        if (category != null) {
            return sweetRepository.findByCategoryIgnoreCase(category)
                    .stream().map(this::mapToResponse).toList();
        }

        if (minPrice != null && maxPrice != null) {
            return sweetRepository.findByPriceBetween(minPrice, maxPrice)
                    .stream().map(this::mapToResponse).toList();
        }

        return getAllSweets();
    }

    @Override
    public SweetResponse updateSweet(Long id, SweetRequest request) {
        Sweet sweet = sweetRepository.findById(id)
                // Use ResourceNotFoundException for 404
                .orElseThrow(() -> new ResourceNotFoundException("Sweet not found with id: " + id));

        sweet.setName(request.getName());
        sweet.setCategory(request.getCategory());
        sweet.setPrice(request.getPrice());
        sweet.setQuantity(request.getQuantity());

        return mapToResponse(sweetRepository.save(sweet));
    }

    @Override
    public void deleteSweet(Long id) {
        // Optional: Check if exists first to return 404 if not found
        if (!sweetRepository.existsById(id)) {
            throw new ResourceNotFoundException("Sweet not found with id: " + id);
        }
        sweetRepository.deleteById(id);
    }

    @Override
    public SweetResponse purchaseSweet(Long id, int quantity) {
        Sweet sweet = sweetRepository.findById(id)
                // Use ResourceNotFoundException for 404
                .orElseThrow(() -> new ResourceNotFoundException("Sweet not found with id: " + id));

        if (sweet.getQuantity() < quantity) {
            // Use BadRequestException for 400
            throw new BadRequestException("Insufficient stock. Available: " + sweet.getQuantity());
        }

        sweet.setQuantity(sweet.getQuantity() - quantity);
        return mapToResponse(sweetRepository.save(sweet));
    }

    @Override
    public SweetResponse restockSweet(Long id, int quantity) {
        Sweet sweet = sweetRepository.findById(id)
                // Use ResourceNotFoundException for 404
                .orElseThrow(() -> new ResourceNotFoundException("Sweet not found with id: " + id));

        sweet.setQuantity(sweet.getQuantity() + quantity);
        return mapToResponse(sweetRepository.save(sweet));
    }

    private SweetResponse mapToResponse(Sweet sweet) {
        return SweetResponse.builder()
                .id(sweet.getId())
                .name(sweet.getName())
                .category(sweet.getCategory())
                .price(sweet.getPrice())
                .quantity(sweet.getQuantity())
                .imageUrl(sweet.getImageUrl())
                .build();
    }
}