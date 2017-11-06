package com.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name = "SubCategory", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class SubCategoryEntity extends BaseEntity{

	private int id;
	private String name;
	private String description;
	private String additionalInfo;
	private String smallImagePath;
    private String largeImagePath; 
	private List<ProductEntity> productList = new ArrayList<>();
//	private BrandEntity brand;
	private CategoryEntity category;
	private boolean active;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(nullable = false, length = 50, name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Column(name = "additional_info")
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "brand_id", nullable = false)
	public BrandEntity getBrand() {
		return brand;
	}
	public void setBrand(BrandEntity brand) {
		this.brand = brand;
	}*/
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "subcategory")
	public List<ProductEntity> getProductList() {
		return productList;
	}
	public void setProductList(List<ProductEntity> productList) {
		this.productList = productList;
	}
	@Column(name = "small_image_path", length = 255)
	public String getSmallImagePath() {
		return smallImagePath;
	}

	public void setSmallImagePath(String smallImagePath) {
		this.smallImagePath = smallImagePath;
	}
	@Column(name = "large_image_path", length = 255)
	public String getLargeImagePath() {
		return largeImagePath;
	}

	public void setLargeImagePath(String largeImagePath) {
		this.largeImagePath = largeImagePath;
	}
	  
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="category_id")
	public CategoryEntity getCategory() {
		return category;
	}
	public void setCategory(CategoryEntity category) {
		this.category = category;
	}
	@Column(name="is_active", columnDefinition="bit default 0", nullable=false)
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
