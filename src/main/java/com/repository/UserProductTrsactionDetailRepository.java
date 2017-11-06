package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entity.UserProductTransacationDetailEntity;

@Repository
public interface UserProductTrsactionDetailRepository extends JpaRepository<UserProductTransacationDetailEntity, Integer> {
}
