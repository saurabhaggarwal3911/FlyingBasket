package com.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.entity.BrandEntity;

@Repository
public interface BrandRepository extends CrudRepository<BrandEntity, Integer> {
	
//	@Query(value="select distinct brand_id from subcategroy__brand where subcategory_id=?1", nativeQuery=true)
//	List<BrandEntity> getBrandIdListBySubCategoryId(Integer subCategoryId);
//	@Query(value="select new BrandEntity(brand.id, brand.name) from BrandEntity brand where brand.active=?1 and brand.subCategoryList.id=?2")
//	List<BrandEntity> findAllByActiveAndSubCategoryId(boolean active, int subCategoryId);

}
