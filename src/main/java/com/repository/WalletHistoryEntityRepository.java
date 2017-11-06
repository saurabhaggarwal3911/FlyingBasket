package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entity.WalletHistoryEntity;

@Repository
public interface WalletHistoryEntityRepository extends JpaRepository<WalletHistoryEntity, Integer> {

	
	
}
