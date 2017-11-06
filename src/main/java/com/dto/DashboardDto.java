package com.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(Include.NON_NULL)
public class DashboardDto {

	private List<CategoryDto> categoryDtoList;
	private List<ProductDto> productDtoList;
	
	public List<CategoryDto> getCategoryDtoList() {
		return categoryDtoList;
	}
	public void setCategoryDtoList(List<CategoryDto> categoryDtoList) {
		this.categoryDtoList = categoryDtoList;
	}
	public List<ProductDto> getProductDtoList() {
		return productDtoList;
	}
	public void setProductDtoList(List<ProductDto> productDtoList) {
		this.productDtoList = productDtoList;
	}
	@Override
	public String toString() {
		return "DashboardDto [categoryDtoList=" + categoryDtoList + ", productDtoList=" + productDtoList + "]";
	}
}
