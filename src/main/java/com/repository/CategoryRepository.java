package com.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.entity.CategoryEntity;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {

	List<CategoryEntity> findAllByActive(boolean active);
}
