package com.entity;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Product_Type__Subcateogry")
@AssociationOverrides({
		@AssociationOverride(name = "compositeKey.product", joinColumns = @JoinColumn(name = "product_id")),
		@AssociationOverride(name = "compositeKey.category", joinColumns = @JoinColumn(name = "category_id")),
		@AssociationOverride(name = "compositeKey.productType", joinColumns = @JoinColumn(name = "product_type_id")),
		@AssociationOverride(name = "compositeKey.subCategory", joinColumns = @JoinColumn(name = "subcategory_id")) })
public class ProductTypeCategoryEntity extends BaseEntity {

	private ProductTypeCategory compositeKey = new ProductTypeCategory();
	private boolean active;

	@Transient
	public ProductEntity getProduct() {
		return getCompositeKey().getProduct();
	}

	public void setProduct(ProductEntity product) {
		this.getCompositeKey().setProduct(product);
	}

	@Transient
	public CategoryEntity getCategory() {
		return getCompositeKey().getCategory();
	}

	public void setCategory(CategoryEntity category) {
		this.getCompositeKey().setCategory(category);
	}
	

	@Transient
	public ProductTypeEntity getProductType() {
		return getCompositeKey().getProductType();
	}

	public void setProductType(ProductTypeEntity productType) {
		this.getCompositeKey().setProductType(productType);
	}

	@Transient
	public SubCategoryEntity getSubCategory() {
		return getCompositeKey().getSubCategory();
	}

	public void setSubCategory(SubCategoryEntity subCategory) {
		this.getCompositeKey().setSubCategory(subCategory);
	}

	@EmbeddedId
	public ProductTypeCategory getCompositeKey() {
		return compositeKey;
	}

	public void setCompositeKey(ProductTypeCategory compositeKey) {
		this.compositeKey = compositeKey;
	}

	@Column(name = "is_active")
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
