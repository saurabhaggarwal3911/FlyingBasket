package com.service;

import java.util.List;

import com.dto.ApplicationConfigurationDto;
import com.dto.LoginDto;


public interface IApplicationConfigurationService {

  List<ApplicationConfigurationDto> getAllSetting();

  LoginDto updatable(LoginDto loginDto);

}
