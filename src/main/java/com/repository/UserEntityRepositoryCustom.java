package com.repository;

import java.util.List;

import com.entity.UserEntity;

public interface UserEntityRepositoryCustom {
  List<UserEntity> executeNativeQuery(String query);
}
