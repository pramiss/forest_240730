package com.forest.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forest.product.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer>{

}
