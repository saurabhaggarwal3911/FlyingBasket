package com.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Product_Screens")
public class ProductScreensEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4283551748596915732L;
	
	private Long id;
	private String path;
	private boolean active;
	private ProductEntity product; 
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(final Long id) {
		this.id = id;
	}
	@Column(name="path", nullable=false)
	public String getPath() {
		return path;
	}
	public void setPath(final String path) {
		this.path = path;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="product_id", nullable=false, updatable=true, insertable=true)
	public ProductEntity getProduct() {
		return product;
	}
	public void setProduct(final ProductEntity product) {
		this.product = product;
	}
	
	@Column(name="is_active", columnDefinition="bit default 0", nullable=false)
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	
}