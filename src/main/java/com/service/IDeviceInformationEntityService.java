package com.service;

import com.dto.DeviceInformationDto;



public interface IDeviceInformationEntityService {


  void saveOrGetDeviceInfoByDeviceId(DeviceInformationDto deviceInformationDto);

  void deleteDeviceInfoByDeviceId(DeviceInformationDto deviceInformationDto);

  void deleteRegIdbyDeviceId(String deviceId);

}
