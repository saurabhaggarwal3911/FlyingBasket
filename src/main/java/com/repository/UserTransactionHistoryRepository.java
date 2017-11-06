package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entity.UserTransactionHistoryEntity;

@Repository
public interface UserTransactionHistoryRepository extends JpaRepository<UserTransactionHistoryEntity, Integer> {

	List<UserTransactionHistoryEntity> findAllByStatus(String status);
}
