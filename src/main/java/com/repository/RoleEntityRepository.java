package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.RoleEntity;

public interface RoleEntityRepository extends JpaRepository<RoleEntity, Integer> {

}
