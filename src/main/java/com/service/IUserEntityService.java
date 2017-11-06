package com.service;

import java.io.IOException;
import java.util.List;

import com.dto.ChangePasswordDto;
import com.dto.CurrentUserDto;
import com.dto.NameIdDto;
import com.dto.RestUserDto;
import com.dto.UserDto;
import com.dto.UserProfileDto;
import com.entity.UserEntity;
import com.exception.DataNotExistException;
import com.exception.UserAlreadyExistException;
import com.exception.UserCanNotRemoveFromPoolException;
import com.exception.UserEmailAlreadyExistException;
import com.exception.UserMobileAlreadyExistException;

public interface IUserEntityService {


    String handleForgotPassword(String email);

    boolean changePassword(ChangePasswordDto changePasswordDto);

    UserProfileDto getUserProfile(CurrentUserDto currentUserDto, boolean showOrders, boolean ShowOrderDetails);

    public void addUser(UserDto userDto) throws UserAlreadyExistException, UserMobileAlreadyExistException, UserEmailAlreadyExistException, DataNotExistException;

    public void addRestUser(RestUserDto restUserDto) throws UserAlreadyExistException, UserMobileAlreadyExistException, UserEmailAlreadyExistException, DataNotExistException;

    public List<UserDto> getUserList(String userType,int pageNum, int MAXRESULT);

    public UserDto getUserById(int id);

    public UserDto getUserByEmail(String name);

    public void editUser(UserDto userDto) throws UserAlreadyExistException, UserCanNotRemoveFromPoolException;

    public List<NameIdDto> searchUser(String userType, String query);

    public long getCount(int maxResult, String userType);

    public UserDto entityToDto(UserEntity entity, UserDto dto);

    UserDto getUserById(int id, boolean isSiteAdmin, boolean isClientAdmin, boolean isWorkspaceAdmin);

	void sendSMS(String mobile) throws IOException;

	List<UserDto> getUserChainTree();



}
