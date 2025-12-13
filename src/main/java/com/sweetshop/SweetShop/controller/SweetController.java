package com.sweetshop.SweetShop.controller;

import com.sweetshop.SweetShop.dto.SweetRequest;
import com.sweetshop.SweetShop.dto.SweetResponse;
import com.sweetshop.SweetShop.service.SweetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sweets")
public class SweetController {

    private final SweetService sweetService;

    public SweetController(SweetService sweetService) {
        this.sweetService = sweetService;
    }

    // POST /api/sweets
    @PostMapping
    public ResponseEntity<SweetResponse> addSweet(
            @RequestBody SweetRequest request
    ) {
        return ResponseEntity.ok(sweetService.addSweet(request));
    }

    // GET /api/sweets
    @GetMapping
    public ResponseEntity<List<SweetResponse>> getAllSweets() {
        return ResponseEntity.ok(sweetService.getAllSweets());
    }

    // GET /api/sweets/search
    @GetMapping("/search")
    public ResponseEntity<List<SweetResponse>> searchSweets(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        return ResponseEntity.ok(
                sweetService.searchSweets(name, category, minPrice, maxPrice)
        );
    }

    // PUT /api/sweets/{id}
    @PutMapping("/{id}")
    public ResponseEntity<SweetResponse> updateSweet(
            @PathVariable Long id,
            @RequestBody SweetRequest request
    ) {
        return ResponseEntity.ok(sweetService.updateSweet(id, request));
    }

    // DELETE /api/sweets/{id}  (ADMIN ONLY – enforced later)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSweet(@PathVariable Long id) {
        sweetService.deleteSweet(id);
        return ResponseEntity.noContent().build();
    }

    // POST /api/sweets/{id}/purchase
    @PostMapping("/{id}/purchase")
    public ResponseEntity<SweetResponse> purchaseSweet(
            @PathVariable Long id,
            @RequestParam int quantity
    ) {
        return ResponseEntity.ok(
                sweetService.purchaseSweet(id, quantity)
        );
    }

    // POST /api/sweets/{id}/restock  (ADMIN ONLY – enforced later)
    @PostMapping("/{id}/restock")
    public ResponseEntity<SweetResponse> restockSweet(
            @PathVariable Long id,
            @RequestParam int quantity
    ) {
        return ResponseEntity.ok(
                sweetService.restockSweet(id, quantity)
        );
    }
}

