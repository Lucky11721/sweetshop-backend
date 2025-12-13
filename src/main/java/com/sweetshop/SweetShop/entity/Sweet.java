package com.sweetshop.SweetShop.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sweets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer quantity;

    @Column(length = 1000)
    private String imageUrl;

}
