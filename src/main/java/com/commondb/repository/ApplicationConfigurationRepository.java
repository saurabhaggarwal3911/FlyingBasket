package com.commondb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.commondb.entity.ApplicationConfiguration;

@Repository
public interface ApplicationConfigurationRepository extends CrudRepository<ApplicationConfiguration, Integer> {

	ApplicationConfiguration findOneByName(String androidVersion);

}
