package com.forest.cart.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartId implements Serializable {

	@Column(name = "userId")
	private int userId;

	@Column(name = "productId")
	private int productId;
}
