package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entity.WalletEntity;

@Repository
public interface WalletEntityRepository extends JpaRepository<WalletEntity, Integer> {

	
	
}
