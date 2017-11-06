package com.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
public class PurchasedItemDto  {
	@NotNull
	@Min(1)
    private Integer id;
    private String title;
    private String category;
    private String brand;
    @NotNull
    @Min(1)
    private Double price;
    @NotNull
    @Min(1)
    private Double total;
    @NotNull
    @Min(1)
    private Integer qty;
    private Integer productId;
    
    
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

    public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	@Override
	public String toString() {
		return "PurchasedItemDto [id=" + id + ", title=" + title + ", category=" + category + ", brand=" + brand
				+ ", price=" + price + ", total=" + total + ", qty=" + qty + ", productId=" + productId + "]";
	}

}
