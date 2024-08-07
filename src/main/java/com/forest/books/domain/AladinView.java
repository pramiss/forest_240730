package com.forest.books.domain;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class AladinView {
	private List<ItemView> item;
}
