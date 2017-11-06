package com.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class ProductTypeCategory implements Serializable {

	private static final long serialVersionUID = 1L;
	private CategoryEntity category;
	private ProductTypeEntity productType;
	private SubCategoryEntity subCategory;
	private ProductEntity product;

	@ManyToOne(cascade = CascadeType.ALL)
	public CategoryEntity getCategory() {
		return category;
	}

	public void setCategory(CategoryEntity category) {
		this.category = category;
	}
	
	@ManyToOne(cascade = CascadeType.ALL)
	public ProductEntity getProduct() {
		return product;
	}

	public void setProduct(ProductEntity product) {
		this.product = product;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	public ProductTypeEntity getProductType() {
		return productType;
	}
	public void setProductType(ProductTypeEntity productType) {
		this.productType = productType;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	public SubCategoryEntity getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategoryEntity subCategory) {
		this.subCategory = subCategory;
	}

}
