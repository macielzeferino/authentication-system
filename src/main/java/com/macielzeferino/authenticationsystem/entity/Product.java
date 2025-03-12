package com.macielzeferino.authenticationsystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;
@Data
@NoArgsConstructor
@Entity
@Table(name="products_table")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_amount")
    private Double productAmount;

    @CreationTimestamp
    private Instant creationTimestamp;

    @UpdateTimestamp
    private Instant updatedTimestamp;

    public Product(String productName, Double productAmount) {
        this.productName = productName;
        this.productAmount = productAmount;
    }


}