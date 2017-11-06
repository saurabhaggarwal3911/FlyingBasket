package com.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Brand", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class BrandEntity extends BaseEntity {

	private int id;
	private String name;
	private String description;
	private String additionalInfo;
//	private List<CategoryEntity> categoryList = new ArrayList<>();
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
	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "brand")
	public List<CategoryEntity> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<CategoryEntity> categoryList) {
		this.categoryList = categoryList;
	}*/
	
	@Column(name="is_active", columnDefinition="bit default 0", nullable=false)
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	
}
