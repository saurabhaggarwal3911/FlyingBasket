package com.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.entity.SubCategoryEntity;

@Repository
public interface SubCategoryRepository extends CrudRepository<SubCategoryEntity, Integer> {

	/*@Query(value="select p from ProductEntity p where p.subcategory.id=?1")
	public List<ProductEntity> findAllBySubCatgoryId(int subcategoryId);
	
	@Query(value="select p from ProductEntity p where p.category.id=?1")
	public List<ProductEntity> findAllByCatgoryId(int categoryId);*/
	
}
