package com.forest.cart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forest.cart.entity.CartEntity;
import com.forest.cart.entity.CartId;

public interface CartRepository extends JpaRepository<CartEntity, CartId> {

	public List<CartEntity> findAllByCartIdUserId(int userId);
}
