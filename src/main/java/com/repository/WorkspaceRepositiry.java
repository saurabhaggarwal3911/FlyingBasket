package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entity.WorkspaceEntity;

@Repository
public interface WorkspaceRepositiry extends JpaRepository<WorkspaceEntity, Integer> {
	
}
