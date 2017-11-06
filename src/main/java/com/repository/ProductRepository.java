package com.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.entity.ProductEntity;
import com.entity.ProductTypeEntity;
import com.entity.SubCategoryEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {


	@Query(value="select p from ProductEntity p where p.subcategory.id=?1 and p.active=?2")
	public List<ProductEntity> findAllBySubCatgoryIdAndActiveAndProductIsNullAndStockIsNotNullOrderByTitleDesc(int subcategoryId, boolean active);
	
	@Query(value="select p from ProductEntity p where p.category.id=?1 and p.active=?2")
	public List<ProductEntity> findAllByCatgoryIdAndActiveAndProductIsNullAndStockIsNotNullOrderByTitleDesc(int categoryId, boolean active);

	 
	public List<ProductEntity> findTop20ByActiveAndStockNotNullAndProductIsNullOrderByIdDesc(boolean active);

	public ProductEntity findOneByIdAndActive(int productId, boolean active);

	public List<ProductEntity> findAllBySubcategoryAndProductTypeAndActiveAndStockNotNullOrderByIdDesc(SubCategoryEntity subcategory,
			ProductTypeEntity productType, boolean active);

	public ProductEntity findById(Long id);
	@Query("select distinct(fetchInfo) from ProductEntity as fetchInfo where fetchInfo.workspaceId = ?1 order by fetchInfo.id asc")
	public List<ProductEntity> findAllByWorkspaceIdPageable(Integer workspaceId, Pageable pageable);
	
	@Query(value="select distinct(fetchInfo) from ProductEntity as fetchInfo where fetchInfo.title like ?1 and fetchInfo.active=true and fetchInfo.stock is not null order by fetchInfo.title asc")
	public List<ProductEntity> findAllByTitleLikeAndStockIsNotNullOrderByTitleAsc(String string, Pageable pageable);

}
