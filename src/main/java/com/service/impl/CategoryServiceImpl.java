package com.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.BannerInfoDto;
import com.dto.CategoryDto;
import com.dto.DashboardDto;
import com.entity.BannerInfoEntity;
import com.entity.CategoryEntity;
import com.entity.SubCategoryEntity;
import com.repository.BannerInfoRepository;
import com.repository.CategoryRepository;
import com.service.ICateogyService;

@Service
public class CategoryServiceImpl implements ICateogyService {
	
//	@Autowired
//	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private BannerInfoRepository bannerInfoRepository;
	
	final DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	@Transactional(timeout = 100000, value = "transactionManager")
	@Override
	public DashboardDto getDashboardProductWithCategoryBrandList() {
		DashboardDto dashboardDto = new DashboardDto();
//		List<ProductEntity> productEntityList = null;
//		List<ProductEntity> productEntityList = productRepository.findTop20ByActiveAndStockNotNullAndProductIsNullOrderByIdDesc(true);
		/*if(productEntityList != null && !productEntityList.isEmpty()){
			List<ProductDto> productDtoList = new ArrayList<>();
			for (ProductEntity productEntity : productEntityList) {
				ProductDto productDto = new ProductDto();
				productEntityToProductDto(productEntity, productDto);
				productDtoList.add(productDto);
			}
			dashboardDto.setProductDtoList(productDtoList);
		}*/
		List<BannerInfoEntity> bannerInfoEntityList = bannerInfoRepository.findAllByActive(true);
		if(bannerInfoEntityList != null && !bannerInfoEntityList.isEmpty()){
			List<BannerInfoDto> bannerInfoDtoList = new ArrayList<>();
			for (BannerInfoEntity bannerInfoEntity : bannerInfoEntityList) {
				BannerInfoDto bannerInfoDto = new BannerInfoDto();
				BeanUtils.copyProperties(bannerInfoEntity, bannerInfoDto);
				bannerInfoDtoList.add(bannerInfoDto);
			}
			dashboardDto.setBannerDtoList(bannerInfoDtoList);
		}
		List<CategoryEntity> categoryEntityList = categoryRepository.findAllByActive(true);
		if(categoryEntityList != null && !categoryEntityList.isEmpty()){
			List<CategoryDto> categoryDtoList = new ArrayList<>();
			for (CategoryEntity categoryEntity : categoryEntityList) {
				CategoryDto categoryDto = new CategoryDto();
				CategoryEntityToCategoryDto(categoryEntity, categoryDto, false, false, true);
				categoryDtoList.add(categoryDto);
			}	
			dashboardDto.setCategoryDtoList(categoryDtoList);
		}
		return dashboardDto;
	}

	private void CategoryEntityToCategoryDto(CategoryEntity categoryEntity, CategoryDto categoryDto, boolean isProductListRequired, boolean isBrandRequired, boolean isSubCategoryRequired) {
		BeanUtils.copyProperties(categoryEntity, categoryDto);
		if(isSubCategoryRequired){
			List<SubCategoryEntity> subcategoryEntityList = categoryEntity.getSubCategoryList();
			if(subcategoryEntityList != null && !subcategoryEntityList.isEmpty()){
				List<CategoryDto> subcategoryDtoList = new ArrayList<>();
				for (SubCategoryEntity subCategoryEntity : subcategoryEntityList) {
					CategoryDto subCategoryDto = new CategoryDto();
					BeanUtils.copyProperties(subCategoryEntity, subCategoryDto);
					subcategoryDtoList.add(subCategoryDto);
				}
				categoryDto.setSubCategoryList(subcategoryDtoList);
			}
		}
		
	}

//	private void productEntityToProductDto(ProductEntity productEntity, ProductDto productDto) {
//		BeanUtils.copyProperties(productEntity, productDto);
//	}
	
}
