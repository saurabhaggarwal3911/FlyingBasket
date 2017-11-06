package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entity.ProductTypeCategoryEntity;

@Repository
public interface ProductTypeCategoryRepository extends JpaRepository<ProductTypeCategoryEntity, Integer> {

//  @Query(value="select distinct(USER_ID)) from [dbo].[Audit_User__History] where sharehistory_id=?1 and USER_ID in ?2", nativeQuery=true)
	List<ProductTypeCategoryEntity> getAllDistinctByCompositeKey_SubCategoryIdAndActive(int subCategoryId, boolean active);
	
}
