package com.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.entity.UserEntity;
import com.repository.UserEntityRepositoryCustom;

public class UserEntityRepositoryImpl implements UserEntityRepositoryCustom {
  @PersistenceContext(unitName = "primary")
  private EntityManager entityManager;

  @Override
  public List<UserEntity> executeNativeQuery(String query) {
    // TODO Auto-generated method stub
    Query namedQuery = entityManager.createNativeQuery(query, UserEntity.class);
    return namedQuery.getResultList();
  }


}
