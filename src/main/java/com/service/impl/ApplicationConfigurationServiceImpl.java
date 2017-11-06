package com.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commondb.entity.ApplicationConfiguration;
import com.commondb.repository.ApplicationConfigurationRepository;
import com.dto.ApplicationConfigurationDto;
import com.dto.LoginDto;
import com.service.IApplicationConfigurationService;
import com.utility.ApplicationConfigurationEnum;

@Service
public class ApplicationConfigurationServiceImpl implements IApplicationConfigurationService {
	@Autowired
	private ApplicationConfigurationRepository applicationConfigurationDao;

	@Transactional(readOnly = true, timeout = 100000, value = "transactionManagerForCommonDb")
	@Override
	public List<ApplicationConfigurationDto> getAllSetting() {
		List<ApplicationConfigurationDto> list = new ArrayList<>();
		Iterable<ApplicationConfiguration> findAll = applicationConfigurationDao.findAll();
		if (findAll != null) {
			Iterator<ApplicationConfiguration> iterator = findAll.iterator();
			while (iterator.hasNext()) {
				ApplicationConfiguration applicationConfiguration = (ApplicationConfiguration) iterator.next();
				ApplicationConfigurationDto confDto = new ApplicationConfigurationDto();
				entityToDto(applicationConfiguration, confDto);
				list.add(confDto);
			}
		}
		return list;
	}

	private void entityToDto(ApplicationConfiguration applicationConfiguration, ApplicationConfigurationDto confDto) {
		// TODO Auto-generated method stub
		confDto.setId(applicationConfiguration.getId());
		confDto.setName(applicationConfiguration.getName());
		confDto.setValue(applicationConfiguration.getValue());
	}

	@Transactional(readOnly = true, timeout = 100000, value = "transactionManagerForCommonDb")
	@Override
	public LoginDto updatable(LoginDto loginDto) {
		ApplicationConfiguration applicationConfiguration = applicationConfigurationDao.findOneByName(ApplicationConfigurationEnum.ANDROID_VERSION+"");
		if(applicationConfiguration != null && loginDto.getVersionNum() != null && !applicationConfiguration.getValue().equalsIgnoreCase(loginDto.getVersionNum())){
			loginDto.setUpdatable(true);
			loginDto.setAppUrl(applicationConfigurationDao.findOneByName(ApplicationConfigurationEnum.ANDROID_DOWNLOAD_URL+"").getValue());
		}
		return loginDto;
	}

}
