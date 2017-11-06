package com.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
public class CategoryBrandProductDto {

	private String type;
	private Set<ProductDto> productList;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Set<ProductDto> getProductList() {
		return productList;
	}
	public void setProductList(Set<ProductDto> productList) {
		this.productList = productList;
	}
}
