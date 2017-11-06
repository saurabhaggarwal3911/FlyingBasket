package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.entity.DeviceInformationEntity;

@Repository
public interface DeviceInformationRepository extends CrudRepository<DeviceInformationEntity, String> {
  public DeviceInformationEntity findOneByRegId(String regId);
  @Query(value="select a from DeviceInformationEntity a where a.user.id=?1")
  public List<DeviceInformationEntity> findAllByUserId(int userId);
}
