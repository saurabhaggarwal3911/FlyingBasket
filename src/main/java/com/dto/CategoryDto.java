package com.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(Include.NON_NULL)
public class CategoryDto {

	
	private int id;
	private String name;
	private String description;
	private String additionalInfo;
	private String smallImagePath;
    private String largeImagePath; 
	private List<CategoryDto> subCategoryList;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
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
	public List<CategoryDto> getSubCategoryList() {
		return subCategoryList;
	}
	public void setSubCategoryList(List<CategoryDto> subCategoryList) {
		this.subCategoryList = subCategoryList;
	}
	@Override
	public String toString() {
		return "CategoryDto [id=" + id + ", name=" + name + ", description=" + description + ", additionalInfo="
				+ additionalInfo + ", smallImagePath=" + smallImagePath + ", largeImagePath=" + largeImagePath
				+ ", subCategoryList=" + subCategoryList + "]";
	}
}
