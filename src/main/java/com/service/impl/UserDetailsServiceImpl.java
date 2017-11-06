package com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.CurrentUserDto;
import com.entity.UserEntity;
import com.repository.UserEntityRepository;
import com.service.IUserDetailsService;

@Service
public class UserDetailsServiceImpl implements IUserDetailsService {
  @Autowired
  private UserEntityRepository userEntityDao;

  @Transactional(readOnly = true, timeout = 100000, value = "transactionManager")
  @Override
  public CurrentUserDto loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity user = userEntityDao.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("User " + username + " not found.");
    }

    else {
      return new CurrentUserDto(user);
    }
  }
}
