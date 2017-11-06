package com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.RoleDto;
import com.entity.RoleEntity;
import com.repository.RoleEntityRepository;
import com.service.IRoleService;

@Service
public class RoleServiceImpl implements IRoleService {
  @Autowired
  private RoleEntityRepository roleEntityRepository;


  @Override
  @Transactional(timeout = 100000, value = "transactionManager")
  public RoleDto getRoleById(int roleId) {
    // TODO Auto-generated method stub
    RoleEntity roleEntity = roleEntityRepository.findOne(roleId);
    RoleDto roleDto = new RoleDto();
    entityToDto(roleEntity, roleDto);
    return roleDto;
  }

  private void entityToDto(RoleEntity roleEntity, RoleDto roleDto) {
    // Tprivate void entityToDto(RoleEntity com.entity, RoleDto com.dto){
    roleDto.setId(roleEntity.getId());
    roleDto.setName(roleEntity.getName().contains("ROLE_")?roleEntity.getName().substring(5): roleEntity.getName());
  }
}
