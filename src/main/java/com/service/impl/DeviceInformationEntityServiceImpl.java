package com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.DeviceInformationDto;
import com.entity.DeviceInformationEntity;
import com.entity.UserEntity;
import com.repository.DeviceInformationRepository;
import com.repository.UserEntityRepository;
import com.service.IDeviceInformationEntityService;

@Service
public class DeviceInformationEntityServiceImpl implements IDeviceInformationEntityService {

  // private Logger LOGGER = Logger.getLogger(DeviceInformationEntityServiceImpl.class);

  @Autowired
  private DeviceInformationRepository deviceInformationEntityDao;
  @Autowired
  private UserEntityRepository userEntityDao;


  @Transactional(value = "transactionManager")
  @Override
  public void saveOrGetDeviceInfoByDeviceId(DeviceInformationDto deviecInfoDto) {

    String deviceId = deviecInfoDto.getDeviceId();
    DeviceInformationEntity deviceInfoByDeviceId = deviceInformationEntityDao.findOne(deviceId);
    if (deviceInfoByDeviceId == null) {
      deviceInfoByDeviceId = new DeviceInformationEntity();
      dtoToEntity(deviecInfoDto, deviceInfoByDeviceId);
      deviceInformationEntityDao.save(deviceInfoByDeviceId);
    } else {
      dtoToEntity(deviecInfoDto, deviceInfoByDeviceId);
      deviceInformationEntityDao.save(deviceInfoByDeviceId);
    }
  }

  private void dtoToEntity(DeviceInformationDto deviecInfoDto, DeviceInformationEntity entity) {
    if (deviecInfoDto.getRegId() != null) {
      entity.setRegId(deviecInfoDto.getRegId());
    }
    entity.setVersionNum(deviecInfoDto.getVersionNum());
    entity.setDeviceId(deviecInfoDto.getDeviceId());
    UserEntity userEntity = userEntityDao.getOne(deviecInfoDto.getUserId());
    entity.setUser(userEntity);
  }

  @Transactional(value = "transactionManager")
  @Override
  public void deleteDeviceInfoByDeviceId(DeviceInformationDto deviceInformationDto) {

    String deviceId = deviceInformationDto.getDeviceId();
    deleteRegIdbyDeviceId(deviceId);
  }

  @Transactional(value = "transactionManager")
  @Override
  public void deleteRegIdbyDeviceId(String deviceId) {
    DeviceInformationEntity deviceInfoByDeviceId = deviceInformationEntityDao.findOne(deviceId);
    if (deviceInfoByDeviceId != null) {
      deviceInformationEntityDao.delete(deviceInfoByDeviceId);
    }
  }

}
