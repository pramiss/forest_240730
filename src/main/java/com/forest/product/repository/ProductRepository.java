package com.forest.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forest.product.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer>{

	public List<ProductEntity> findByIsbn(String isbn);
	public List<ProductEntity> findByIsbnOrderByPriceDesc(String isbn);
	public List<ProductEntity> findAllByOrderByIdDesc();
    public List<ProductEntity> findAllByOrderBySaleStatusDescIsbnDesc();
}
