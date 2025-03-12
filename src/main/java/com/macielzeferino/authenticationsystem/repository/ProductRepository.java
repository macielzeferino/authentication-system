package com.macielzeferino.authenticationsystem.repository;

import com.macielzeferino.authenticationsystem.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
