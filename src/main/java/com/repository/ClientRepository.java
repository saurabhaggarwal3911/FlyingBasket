package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entity.ClientEntity;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Integer> {
}
