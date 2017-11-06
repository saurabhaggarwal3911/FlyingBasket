package com.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ProductDto implements Comparable<ProductDto> {
    private int id;
    private String title;
    private String description;
    private String code;
    
    private NameIdDto category;
    private NameIdDto brand;
    private NameIdDto subcategory;
    private NameIdDto productType;;

    private String smallImagePath;
    private String largeImagePath; 
    private List<String> images;
    private List<String> suggestion;
    private List<String> allegations;
    
    private Date expDate;
    private Date mfdDate;
    private String size;
    private String offer;
    private Double oldPrice;
    private Double price;
    private String stock;
  
    private List<ProductDto> productVarientDtoList;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public NameIdDto getCategory() {
		return category;
	}

	public void setCategory(NameIdDto category) {
		this.category = category;
	}

	public NameIdDto getBrand() {
		return brand;
	}

	public NameIdDto getProductType() {
		return productType;
	}

	public void setProductType(NameIdDto productType) {
		this.productType = productType;
	}

	public void setBrand(NameIdDto brand) {
		this.brand = brand;
	}

	public NameIdDto getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(NameIdDto subcategory) {
		this.subcategory = subcategory;
	}

	public String getSmallImagePath() {
		return smallImagePath;
	}

	public void setSmallImagePath(String smallImagePath) {
		this.smallImagePath = smallImagePath;
	}

	public String getLargeImagePath() {
		return largeImagePath;
	}

	public void setLargeImagePath(String largeImagePath) {
		this.largeImagePath = largeImagePath;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public List<String> getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(List<String> suggestion) {
		this.suggestion = suggestion;
	}

	public List<String> getAllegations() {
		return allegations;
	}

	public void setAllegations(List<String> allegations) {
		this.allegations = allegations;
	}

	public Date getExpDate() {
		return expDate;
	}

	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}

	public Date getMfdDate() {
		return mfdDate;
	}

	public void setMfdDate(Date mfdDate) {
		this.mfdDate = mfdDate;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getOffer() {
		return offer;
	}

	public void setOffer(String offer) {
		this.offer = offer;
	}

	public Double getOldPrice() {
		return oldPrice;
	}

	public void setOldPrice(Double oldPrice) {
		this.oldPrice = oldPrice;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	
	public List<ProductDto> getProductVarientDtoList() {
		return productVarientDtoList;
	}

	public void setProductVarientDtoList(List<ProductDto> productVarientDtoList) {
		this.productVarientDtoList = productVarientDtoList;
	}

	@Override
    public int compareTo(ProductDto obj) {
	int x = String.CASE_INSENSITIVE_ORDER.compare(this.getTitle(), obj.getTitle());
	if (x == 0) {
	    x = this.getTitle().compareTo(obj.getTitle());
	}
	return x;
    }

    @Override
    public boolean equals(Object obj) {
	// TODO Auto-generated method stub
	if (this == obj) {
	    return true;
	}
	if (this == null || obj == null) {
	    return false;
	}
	if (this.getId() == ((ProductDto) obj).getId()) {
	    return true;
	} else {
	    return false;
	}
    }

    @Override
    public int hashCode() {
	// TODO Auto-generated method stub
	return id;
    }

    @Override
	public String toString() {
		return "ProductDto [id=" + id + ", title=" + title + ", description=" + description + ", code=" + code
				+ ", category=" + category + ", brand=" + brand + ", subcategory=" + subcategory + ", productType="
				+ productType + ", smallImagePath=" + smallImagePath + ", largeImagePath=" + largeImagePath
				+ ", images=" + images + ", suggestion=" + suggestion + ", allegations=" + allegations + ", expDate="
				+ expDate + ", mfdDate=" + mfdDate + ", size=" + size + ", offer=" + offer + ", oldPrice=" + oldPrice
				+ ", price=" + price + ", stock=" + stock + ", productVarientDtoList=" + productVarientDtoList + "]";
	}

}
