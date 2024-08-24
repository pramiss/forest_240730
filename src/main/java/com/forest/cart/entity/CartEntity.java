package com.forest.cart.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@Table(name = "cart")
@Entity
public class CartEntity {

	@EmbeddedId
	private CartId cartId;
	
	@CreationTimestamp
	@Column(name = "createdAt")
	private LocalDateTime createdAt;
}
