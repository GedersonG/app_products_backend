package com.app_products.app_products.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="product")
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private int id;
    private String name;
    private float price;
}
