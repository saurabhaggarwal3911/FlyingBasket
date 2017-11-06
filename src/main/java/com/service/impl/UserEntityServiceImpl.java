package com.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth.SecurityUtil;
import com.commondb.repository.ApplicationConfigurationRepository;
import com.dto.ChangePasswordDto;
import com.dto.CheckoutDto;
import com.dto.CurrentUserDto;
import com.dto.EmailDto;
import com.dto.NameIdDto;
import com.dto.PurchasedItemDto;
import com.dto.RestUserDto;
import com.dto.UserDto;
import com.dto.UserProfileDto;
import com.entity.UserEntity;
import com.entity.UserProductTransacationDetailEntity;
import com.entity.UserTransactionHistoryEntity;
import com.entity.WorkspaceEntity;
import com.exception.DataNotExistException;
import com.exception.UserAlreadyExistException;
import com.exception.UserCanNotRemoveFromPoolException;
import com.exception.UserEmailAlreadyExistException;
import com.exception.UserMobileAlreadyExistException;
import com.repository.ClientRepository;
import com.repository.RoleEntityRepository;
import com.repository.UserEntityRepository;
import com.repository.WorkspaceRepositiry;
import com.service.IEmailService;
import com.service.ISmsService;
import com.service.IUserEntityService;
import com.utility.ApplicationConfigurationEnum;
import com.utility.ApplicationConstants;
import com.utility.GeneralUtil;
import com.utility.MailSmsContents;
import com.utility.PasswordUtility;

@Service
public class UserEntityServiceImpl implements IUserEntityService {
	private static final Logger LOGGER = LogManager.getLogger(UserEntityServiceImpl.class);
	// @Autowired
	// private IFileService fileService;
	@Autowired
	private UserEntityRepository userEntityRepository;
	@Autowired
	private RoleEntityRepository roleEntityRepository;
	@Autowired
	private IEmailService emailService;
	@Autowired
	private SecurityUtil securityUtil;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private WorkspaceRepositiry workspaceRepositiry;
	@Autowired
	private ISmsService smsService;
	@Autowired
	private ApplicationConfigurationRepository applicationConfigurationDao;


	@Transactional(timeout = 100000, value = "transactionManager")
	@Override
	public String handleForgotPassword(final String emailId) {
		UserEntity userEntity = userEntityRepository.findByUsername(emailId);
		if (userEntity == null || userEntity.isDeleteUser()) {
			return "userNotFound";
		}
		boolean valid = userEntity.isValid();
		if (!valid) {
			return "accountDisabled";
		}
		final String password = new PasswordUtility.SessionIdentifierGenerator().nextSessionId();
		String encodePassword = PasswordUtility.encodePassword(password);
		
		final String userEntityName = userEntity.getName();
		final String userEntityEmail = userEntity.getUsername();
		final String userEntityMobile = userEntity.getMobile();
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				
				try {
					smsService.sendSMS(userEntityMobile, MailSmsContents.ForgotPassword.getSMSBody(userEntityName, password));
				} catch (IOException e) {
					LOGGER.error(e);
				}
				try {
					EmailDto emailDto = new EmailDto();
					emailDto.setTo(new String[]{userEntityEmail});
					emailDto.setMailBody(MailSmsContents.ForgotPassword.getMailBody(userEntityName, password));
					emailDto.setSubject(MailSmsContents.ForgotPassword.MAILSUBJECT);
					emailService.sendMail(emailDto);
				} catch (MessagingException e) {
					 
					LOGGER.error("Error in send forgot password mail", e);
				}
				
				
			}
		});

		thread.start();
		userEntity.setPassword(encodePassword);
		userEntityRepository.save(userEntity);
		securityUtil.logoutManully(userEntity.getId());
		return "success";
	}

	@Transactional(value = "transactionManager")
	@Override
	public boolean changePassword(ChangePasswordDto changePasswordDto) {

		UserEntity userEntity = userEntityRepository.findByUsername(changePasswordDto.getUserId());
		boolean matchesPassword = PasswordUtility.matchesPassword(changePasswordDto.getOldPassword(),
				userEntity.getPassword());
		if (matchesPassword) {
			String encodePassword = PasswordUtility.encodePassword(changePasswordDto.getNewPassword());
			userEntity.setPassword(encodePassword);
			userEntityRepository.save(userEntity);
		}
		return matchesPassword;
	}

	@Transactional(value = "transactionManager")
	@Override
	public void sendSMS(String mobile) throws IOException {
		CurrentUserDto currentUserDto = (CurrentUserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		smsService.sendSMS(mobile, MailSmsContents.ReferralUser.getSMSBody(currentUserDto.getName(), currentUserDto.getReferralCode()));
	}
	
	@Transactional(value = "transactionManager", readOnly = true, timeout = 1000)
	@Override
	public UserProfileDto getUserProfile(CurrentUserDto currentUserDto, boolean showOrders, boolean ShowOrderDetails) {
		UserEntity userEntity = userEntityRepository.findOne(currentUserDto.getId());
		UserProfileDto userProfileDto = new UserProfileDto();
		userProfileDto.setEmail(currentUserDto.getUsername());
		userProfileDto.setName(currentUserDto.getName());
		userProfileDto.setMobile(currentUserDto.getMobile());
		userProfileDto.setAddress(currentUserDto.getAddress());
		userProfileDto.setValid(currentUserDto.isEnabled());
		userProfileDto.setMembershipType(currentUserDto.getMembershipType());
		userProfileDto.setReferralCode(userEntity.getReferenceCode());
		if(userEntity.getReferenceCode() != null){
			userProfileDto.setAppUrl(applicationConfigurationDao.findOneByName(ApplicationConfigurationEnum.ANDROID_DOWNLOAD_URL+"").getValue());
		}
		if(applicationConfigurationDao.findOneByName(ApplicationConfigurationEnum.USE_DFG+"").getValue()=="true"){
			userProfileDto.setWallet(0);
		}else{
			userProfileDto.setWallet(userEntity.getWallet() != null? userEntity.getWallet().getAmount() :0);
		}
		if(showOrders){
			Set<UserTransactionHistoryEntity> transactionHistoryList = userEntity.getTransactionHistory();
			if(transactionHistoryList != null && !transactionHistoryList.isEmpty()){
				List<CheckoutDto> checkoutDtoList = new ArrayList<>();
				for (UserTransactionHistoryEntity userTransactionHistoryEntity : transactionHistoryList) {
					CheckoutDto checkoutDto = new CheckoutDto();
					checkoutDto.setId(userTransactionHistoryEntity.getId());
					checkoutDto.setAddress(userTransactionHistoryEntity.getBillAddress());
					checkoutDto.setAmount(userTransactionHistoryEntity.getAmount());
					checkoutDto.setName(userTransactionHistoryEntity.getBillName());
					checkoutDto.setEmailId(userTransactionHistoryEntity.getBillEmail());
					checkoutDto.setMobileNum(userTransactionHistoryEntity.getBillMobile());
					checkoutDto.setDate(userTransactionHistoryEntity.getCreatedOn()+"");
					checkoutDto.setPayMode(userTransactionHistoryEntity.getPayMode());
					checkoutDto.setStatus(userTransactionHistoryEntity.getStatus());
					if(ShowOrderDetails){
						List<PurchasedItemDto> data = checkoutDto.getData();
						List<UserProductTransacationDetailEntity> userProductTransacationDetailList = userTransactionHistoryEntity.getUserProductTransacationDetailList();
						if(userProductTransacationDetailList != null && !userProductTransacationDetailList.isEmpty()){
							for (UserProductTransacationDetailEntity userProductTransacationDetailEntity : userProductTransacationDetailList) {
								PurchasedItemDto purchasedItemDto = new PurchasedItemDto();
								purchasedItemDto.setId(userProductTransacationDetailEntity.getId());
								purchasedItemDto.setPrice(userProductTransacationDetailEntity.getPrice());
								purchasedItemDto.setProductId((int)userProductTransacationDetailEntity.getProductId());
								purchasedItemDto.setTitle(userProductTransacationDetailEntity.getProductName());
								purchasedItemDto.setTotal(userProductTransacationDetailEntity.getTotal());
								purchasedItemDto.setQty(userProductTransacationDetailEntity.getQuanity());
								data.add(purchasedItemDto);
							}
						}
					}
					checkoutDtoList.add(checkoutDto);
				}
				userProfileDto.setOrder(checkoutDtoList);
			}
		}
		
		/*List<WorkspaceEntity> workspaceList = userEntity.getWorkspaceList();
		if (workspaceList != null) {
			List<String> workspaceNameList = new ArrayList<>();
			for (WorkspaceEntity workspaceEntity : workspaceList) {
				if (!workspaceEntity.isActive()) {
					continue;
				}
				workspaceNameList.add(workspaceEntity.getName());
			}
			userProfileDto.setWorkspaceList(workspaceNameList);
		}*/
		return userProfileDto;
	}

	/*
	 * @Override
	 * 
	 * @Transactional(timeout = 100000, value = "transactionManager", readOnly =
	 * true) public List<UserDto> getUserList(String userType, int pageNum, int
	 * limit) { int offset = pageNum - 1; Pageable pageable = new
	 * PageRequest(offset, limit); if (StringUtils.isEmpty(userType) ||
	 * "workspaceUser".equals(userType)) { return
	 * userEntityToDto(userEntityRepository.findAllByWorkspaceIdPageable(
	 * GeneralUtil.getWorkspaceId(), ApplicationConstants.Roles.SITE_ADMIN,
	 * false, pageable)); } else { return
	 * userEntityToDto(userEntityRepository.findByNotRoleIdPageable(
	 * ApplicationConstants.Roles.SITE_ADMIN, false, pageable)); }
	 * 
	 * }
	 */

	/*
	 * @Transactional(timeout = 100000, value = "transactionManager") public
	 * UserDto getUserById(int id) { UserEntity userEntity =
	 * userEntityRepository.findOne(id); if (userEntity == null) { return null;
	 * } UserDto userDto = new UserDto(); UserDto dto = entityToDto(userEntity,
	 * userDto); return dto;
	 * 
	 * }
	 */

	/*
	 * @Transactional(timeout = 100000, value = "transactionManager") public
	 * UserDto getUserByEmail(String userName) { UserDto userDto = new
	 * UserDto(); UserDto dto =
	 * entityToDto(userEntityRepository.findByUsername(userName), userDto);
	 * return dto; }
	 */
	/*
	 * @Transactional(timeout = 100000, value = "transactionManager") public
	 * void editUser(UserDto userDto) throws UserAlreadyExistException,
	 * UserHaveSingleWorkspaceException, UserCanNotRemoveFromPoolException {
	 * 
	 * String email = userDto.getUsername(); Integer id = userDto.getId();
	 * 
	 * if (!checkUniqueUserEmailBeforeUpdate(email, id)) { throw new
	 * UserAlreadyExistException(); }
	 * 
	 * 
	 * UserEntity oldUserEntity = userEntityRepository.findOne(userDto.getId());
	 * 
	 * if (!userDto.isValid()) { if( oldUserEntity.getWorkspace() != null){ long
	 * size = userEntityRepository.countByWorkspaceAndDeleteUser(oldUserEntity.
	 * getWorkspace(), false); if (size == 1L) { throw new
	 * UserHaveSingleWorkspaceException("edituser.singleworkspaceerror"); }
	 * }else if(oldUserEntity.getClient() != null){ long size =
	 * userEntityRepository.countByClientAndDeleteUser(oldUserEntity.
	 * getWorkspace(), false); if (size == 1L) { throw new
	 * UserHaveSingleWorkspaceException("edituser.singleclienterror"); } }
	 * oldUserEntity.getWorkspaceList().clear();
	 * oldUserEntity.setWorkspace(null); oldUserEntity.setClient(null);
	 * userDto.setRole(ApplicationConstants.Roles.USER);
	 * securityUtil.logoutManully(id);
	 * 
	 * }else{ Integer clientId2 = GeneralUtil.getClientId(); Integer workspaceId
	 * = GeneralUtil.getWorkspaceId(); WorkspaceEntity workspace =
	 * oldUserEntity.getWorkspace(); if(clientId2 != null){
	 * oldUserEntity.getWorkspaceList().clear();
	 * 
	 * List<Integer> workspaceList = userDto.getWorkspaceList();
	 * if(workspace!=null &&
	 * (workspaceList==null||!workspaceList.contains(workspace.getId()))){
	 * //workspace admin should always be workspace user. we can not remove
	 * admin frm workspace pool throw new
	 * UserCanNotRemoveFromPoolException(workspace.getName(), "workspace"); }
	 * if(workspaceList != null){ for (Integer workspaceId3 : workspaceList) {
	 * WorkspaceEntity workspaceEntity =
	 * workspaceRepositiry.getOne(workspaceId3);
	 * oldUserEntity.getWorkspaceList().add(workspaceEntity); } } }else
	 * if(workspaceId != null){ boolean availableInCurrentWorkspace =
	 * userDto.isAvailableInCurrentWorkspace(); long count =
	 * userEntityRepository.countByUserIdAndWorkspaceId(id, workspaceId);
	 * if(availableInCurrentWorkspace){ if(count==0L){ WorkspaceEntity
	 * workspaceEntity = workspaceRepositiry.getOne(workspaceId);
	 * oldUserEntity.getWorkspaceList().add(workspaceEntity); } }else
	 * if(workspace!=null && workspace.getId()==workspaceId){ //workspace admin
	 * should always be workspace user. we can not remove admin frm workspace
	 * pool throw new UserCanNotRemoveFromPoolException(workspace.getName(),
	 * "workspace"); }else if(count!=0L){ WorkspaceEntity workspaceEntity =
	 * workspaceRepositiry.getOne(workspaceId);
	 * oldUserEntity.getWorkspaceList().remove(workspaceEntity); } }
	 * 
	 * }
	 * 
	 * oldUserEntity.setUsername(userDto.getUsername());
	 * oldUserEntity.setName(userDto.getName());
	 * oldUserEntity.setRole(roleEntityRepository.getOne(userDto.getRole()));
	 * oldUserEntity.setValid(userDto.isValid());
	 * userEntityRepository.save(oldUserEntity);
	 * 
	 * }
	 * 
	 * private List<UserDto> userEntityToDto(List<UserEntity> userEntityList) {
	 * if (userEntityList == null || userEntityList.isEmpty()) { return new
	 * ArrayList<UserDto>(); } else { List<UserDto> dtoList = new
	 * ArrayList<UserDto>(); for (UserEntity entity : userEntityList) { UserDto
	 * dto = new UserDto(); entityToDto(entity, dto); dtoList.add(dto); } return
	 * dtoList; } }
	 * 
	 * @Override public UserDto entityToDto(UserEntity entity, UserDto dto) {
	 * 
	 * dto.setEmail(entity.getUsername()); dto.setName(entity.getName());
	 * dto.setRole(entity.getRole().getId()); dto.setId(entity.getId());
	 * dto.setValid(entity.isValid()); WorkspaceEntity workspace =
	 * entity.getWorkspace(); if(workspace != null){
	 * dto.setAdminForWorkspaceName(workspace.getName()); } ClientEntity client
	 * = entity.getClient(); if(client != null){
	 * dto.setAdminForClientName(client.getName()); }
	 * 
	 * dto.setLoginInfo(entity.getLoginInfo()); return dto; }
	 * 
	 * @Override
	 * 
	 * @Transactional(timeout = 100000, value = "transactionManager") public
	 * void addUser(final UserDto userDto) throws UserAlreadyExistException {
	 * String emailId = userDto.getUsername(); UserEntity userEntityByEmail =
	 * userEntityRepository.findByUsername(emailId); if (userEntityByEmail !=
	 * null) { throw new UserAlreadyExistException(); } UserEntity userEntity =
	 * userDtoToEntity(userDto, new UserEntity());
	 * userEntityRepository.save(userEntity);
	 * 
	 * Thread thread = new Thread(new Runnable() {
	 * 
	 * @Override public void run() { EmailDto emailDto = new EmailDto().new
	 * AddUserEmailDto(servletContext.getAttribute(ApplicationConfigurationEnum.
	 * APP_NAME.toString()).toString(), userDto.getName(),
	 * userDto.getUsername(), userDto.getPassword(),
	 * servletContext.getAttribute(
	 * ApplicationConfigurationEnum.APP_URL.toString()).toString());
	 * emailDto.setTo(new String[] {userDto.getUsername()}); try {
	 * emailService.sendMail(emailDto); } catch (MessagingException e) {
	 * LOGGER.error("Error in send forgot password mail", e); }
	 * 
	 * } });
	 * 
	 * thread.start(); }
	 * 
	 * private UserEntity userDtoToEntity(UserDto dto, UserEntity entity) {
	 * if(entity==null){ entity=new UserEntity(); } if(dto.getId()
	 * !=null&&dto.getId() != 0){ entity.setId(dto.getId()); }
	 * if(dto.getPassword() != null){
	 * entity.setPassword(PasswordUtility.encodePassword(dto.getPassword())); }
	 * entity.setName(dto.getName());
	 * entity.setRole(roleEntityRepository.findOne(dto.getRole())); //comment
	 * toc because of seprate db //
	 * entity.setToc(tocEntityDao.getTocByCode(dto.getTocCode()));
	 * entity.setUsername(dto.getUsername()); entity.setValid(dto.isValid());
	 * return entity; }
	 * 
	 * @Override
	 * 
	 * @Transactional(timeout = 100000, value = "transactionManager", readOnly =
	 * true) public Long getWorkspaceAdminCountByWorkspaceId(Integer
	 * workspaceId) { Long count =
	 * userEntityRepository.countByWorkspaceIdAndRoleIdAndDelete(workspaceId,
	 * ApplicationConstants.Roles.WORKSPACE_ADMIN, false); return count; }
	 * 
	 * @Override
	 * 
	 * @Transactional(timeout = 100000, value = "transactionManager") public
	 * List<NameIdDto> searchUser(String userType, String query) { final int
	 * maxResult = 50; List<UserEntity> userEntityList = null; if
	 * (StringUtils.isEmpty(userType) || "workspaceUser".equals(userType)) {
	 * userEntityList =
	 * userEntityRepository.findIdNameAllByWorkspaceIdAndNameLike(GeneralUtil.
	 * getWorkspaceId(), ApplicationConstants.Roles.SITE_ADMIN, false, query +
	 * "%", new PageRequest(0, maxResult)); } else if
	 * ("manualUser".equals(userType)) { userEntityList =
	 * userEntityRepository.findIdNameByRoleIdAndAdUserAndNameLike(
	 * ApplicationConstants.Roles.SITE_ADMIN, false, false, query + "%", new
	 * PageRequest(0, maxResult)); } else if ("adUser".equals(userType)) {
	 * userEntityList =
	 * userEntityRepository.findIdNameByRoleIdAndAdUserAndNameLike(
	 * ApplicationConstants.Roles.SITE_ADMIN, true, false, query + "%", new
	 * PageRequest(0, maxResult)); } else { userEntityList =
	 * userEntityRepository.findIdNameByRoleIdAndNameLike(ApplicationConstants.
	 * Roles.SITE_ADMIN, false, query + "%", new PageRequest(0, maxResult)); }
	 * if (userEntityList==null) { userEntityList = new ArrayList<UserEntity>();
	 * } if (userEntityList.size() < maxResult) { if
	 * (StringUtils.isEmpty(userType) || "workspaceUser".equals(userType)) {
	 * userEntityList.addAll(userEntityRepository.
	 * findIdNameAllByWorkspaceIdAndNameLike(GeneralUtil.getWorkspaceId(),
	 * ApplicationConstants.Roles.SITE_ADMIN, false, "% " + query + "%", new
	 * PageRequest(0, maxResult - userEntityList.size()))); } else if
	 * ("manualUser".equals(userType)) {
	 * userEntityList.addAll(userEntityRepository.
	 * findIdNameByRoleIdAndAdUserAndNameLike(ApplicationConstants.Roles.
	 * SITE_ADMIN, false, false, "% " + query + "%", new PageRequest(0,
	 * maxResult - userEntityList.size()))); } else if
	 * ("adUser".equals(userType)) { userEntityList.addAll(userEntityRepository.
	 * findIdNameByRoleIdAndAdUserAndNameLike(ApplicationConstants.Roles.
	 * SITE_ADMIN, true, false, "% " + query + "%", new PageRequest(0, maxResult
	 * - userEntityList.size()))); } else {
	 * userEntityList.addAll(userEntityRepository.findIdNameByRoleIdAndNameLike(
	 * ApplicationConstants.Roles.SITE_ADMIN, false, "% " + query + "%", new
	 * PageRequest(0, maxResult - userEntityList.size()))); } }
	 * 
	 * 
	 * List<NameIdDto> userList = new ArrayList<NameIdDto>(); if (userEntityList
	 * != null) { for (UserEntity userEntity : userEntityList) { NameIdDto
	 * nameIdDto = new NameIdDto(); nameIdDto.setId(userEntity.getId());
	 * nameIdDto.setName(userEntity.getName()); userList.add(nameIdDto); } }
	 * return userList;
	 * 
	 * }
	 * 
	 * private boolean checkUniqueUserEmailBeforeUpdate(String emailId, Integer
	 * id) {
	 * 
	 * boolean isEmailUnique = true;
	 * 
	 * UserEntity user = userEntityRepository.findByUsernameAndIdNot(emailId,
	 * id); if (null != user) { isEmailUnique = false; } return isEmailUnique;
	 * 
	 * }
	 * 
	 * @Override
	 * 
	 * @Transactional(timeout = 100000, value = "transactionManager", readOnly =
	 * true) public long getCount(int maxResult, String userType) { long count =
	 * 0L;
	 * 
	 * if (StringUtils.isEmpty(userType) || "workspaceUser".equals(userType)) {
	 * count = userEntityRepository.countByWorkspaceIdPageable(GeneralUtil.
	 * getWorkspaceId(), ApplicationConstants.Roles.SITE_ADMIN, false); } else
	 * if ("manualUser".equals(userType)) { count =
	 * userEntityRepository.countByRoleIdAndAdUserPageable(ApplicationConstants.
	 * Roles.SITE_ADMIN, false, false); } else if ("adUser".equals(userType)) {
	 * count =
	 * userEntityRepository.countByRoleIdAndAdUserPageable(ApplicationConstants.
	 * Roles.SITE_ADMIN, true, false); } else { count =
	 * userEntityRepository.countByRoleIdPageable(ApplicationConstants.Roles.
	 * SITE_ADMIN, false); }
	 * 
	 * 
	 * 
	 * if (count % maxResult == 0) { count = count / maxResult; } else { count =
	 * count / maxResult + 1; } return count; }
	 * 
	 * 
	 *//**
		 * This method will add user in db if user exist in Active Directory.
		 * 
		 *//*
		 * @Override
		 * 
		 * @Transactional(value = "transactionManager") public void
		 * addUserFromLDap(PersonDto personDto) { UserEntity userEntity = new
		 * UserEntity(); userEntity.setUsername(personDto.getEmail());
		 * userEntity.setRole(roleEntityRepository.getOne(ApplicationConstants.
		 * Roles.USER)); userEntity.setName(personDto.getName());
		 * userEntity.setValid(true);
		 * userEntity.setObjectGuid(personDto.getObjectGuid());
		 * userEntity.setAccountName(personDto.getAccountName());
		 * userEntity.setCreatedOn(new Date()); userEntity.setAdUser(true);
		 * userEntityRepository.save(userEntity); }
		 */

	/*
	 * 
	 * @Override public UserDto getUserById(int id, boolean isSiteAdmin, boolean
	 * isClientAdmin, boolean isWorkspaceAdmin) { UserEntity userEntity =
	 * userEntityRepository.findOne(id); UserDto userDto = new UserDto();
	 * entityToDto(userEntity, userDto); if(userEntity.isValid() &&
	 * !userEntity.isDeleteUser()){ if(isClientAdmin){ List<WorkspaceEntity>
	 * workspaceList = userEntity.getWorkspaceList();
	 * if(!workspaceList.isEmpty()){ List<Integer> workspaceIdList = new
	 * ArrayList<>(); for (WorkspaceEntity workspaceEntity : workspaceList) {
	 * workspaceIdList.add(workspaceEntity.getId()); }
	 * userDto.setWorkspaceList(workspaceIdList); } } if(isWorkspaceAdmin){
	 * Integer workspaceId = GeneralUtil.getWorkspaceId(); long
	 * countByUserIdAndWorkspaceId =
	 * userEntityRepository.countByUserIdAndWorkspaceId(id, workspaceId);
	 * userDto.setAvailableInCurrentWorkspace(countByUserIdAndWorkspaceId==0L?
	 * false:true); } } return userDto; }
	 */

	/*
	 * private byte[] getFileByte(Integer fileId) { FileDto file =
	 * fileService.getFile(fileId); return file.getFileByte(); }
	 */
	/*
	 * @Override
	 * 
	 * @Transactional(timeout = 100000, value = "transactionManager", readOnly =
	 * true) public List<NameIdDto>
	 * findIdNameAllByRoleIdAndNameLike(UserSearchCriteriaDto
	 * userSearchCriteriaDto) { final int maxResult = 50; int roleId =
	 * userSearchCriteriaDto.getRoleId(); String query =
	 * userSearchCriteriaDto.getStr(); boolean valid =
	 * userSearchCriteriaDto.isValid(); List<UserEntity> userEntityList =
	 * userEntityRepository.findIdNameAllByRoleIdAndNameLike(roleId, false,
	 * query + "%", valid, new PageRequest(0, maxResult));
	 * 
	 * if (userEntityList == null) { userEntityList = new
	 * ArrayList<UserEntity>(); }
	 * 
	 * if (userEntityList.size() < maxResult) {
	 * userEntityList.addAll(userEntityRepository.
	 * findIdNameAllByRoleIdAndNameLike(roleId, false, "% " + query + "%",
	 * valid, new PageRequest(0, maxResult-userEntityList.size()))); }
	 * 
	 * List<NameIdDto> userList = new ArrayList<NameIdDto>(); if (userEntityList
	 * != null) { for (UserEntity userEntity : userEntityList) { NameIdDto
	 * nameIdDto = new NameIdDto(); nameIdDto.setId(userEntity.getId());
	 * nameIdDto.setName(userEntity.getName()); userList.add(nameIdDto); } }
	 * return userList; }
	 */

	/*
	 * @Override
	 * 
	 * @Transactional(timeout = 100000, value = "transactionManager") public
	 * List<UserEntity> searchUser(UserSearchCriteriaDto userSearchCriteriaDto,
	 * int maxResult) { int roleId = Roles.USER; String query =
	 * userSearchCriteriaDto.getStr(); boolean valid = true; List<Integer>
	 * idListNotInResult = userSearchCriteriaDto.getIdListNotInResult();
	 * List<UserEntity> userEntityList = null; if (idListNotInResult == null ||
	 * idListNotInResult.isEmpty()) { userEntityList =
	 * userEntityRepository.findIdNameAllByRoleIdAndNameLike(roleId, false,
	 * query + "%", valid, new PageRequest(0, maxResult)); } else {
	 * userEntityList =
	 * userEntityRepository.findIdNameAllByRoleIdAndNameLike(roleId, false,
	 * query + "%", valid, idListNotInResult, new PageRequest(0, maxResult)); }
	 * 
	 * 
	 * if (userEntityList == null) { userEntityList = new
	 * ArrayList<UserEntity>(); }
	 * 
	 * if (userEntityList.size() < maxResult) { if (idListNotInResult == null ||
	 * idListNotInResult.isEmpty()) {
	 * userEntityList.addAll(userEntityRepository.
	 * findIdNameAllByRoleIdAndNameLike(roleId, false, "% " + query + "%",
	 * valid, new PageRequest(0, maxResult - userEntityList.size()))); } else {
	 * userEntityList.addAll(userEntityRepository.
	 * findIdNameAllByRoleIdAndNameLike(roleId, false, "% " + query + "%",
	 * valid, idListNotInResult, new PageRequest(0, maxResult -
	 * userEntityList.size()))); } } return userEntityList;
	 * 
	 * }
	 */

	@Override
	@Transactional(timeout = 100000, value = "transactionManager")
	  public UserDto getUserById(int id) {
	    UserEntity userEntity = userEntityRepository.findOne(id);
	    if (userEntity == null) {
	      return null;
	    }
	    UserDto userDto = new UserDto();
	    UserDto dto = entityToDto(userEntity, userDto);
	    return dto;

	  }

	@Override
	public UserDto getUserByEmail(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(timeout = 100000, value = "transactionManager")
	public void addUser(final UserDto userDto) throws UserAlreadyExistException, UserMobileAlreadyExistException, UserEmailAlreadyExistException, DataNotExistException {
		UserEntity userEntity =userEntityRepository.findByUsernameOrMobile(userDto.getEmail(), userDto.getMobile());
		if (userEntity != null) {
			if(userEntity.getUsername().equalsIgnoreCase(userDto.getEmail()) && userEntity.getMobile().equalsIgnoreCase(userDto.getMobile()))
			{
				throw new UserAlreadyExistException();
			} else if((userEntity.getUsername().equalsIgnoreCase(userDto.getEmail()))){
				throw new UserEmailAlreadyExistException();
			} else if((userEntity.getMobile().equalsIgnoreCase(userDto.getMobile()))){
				throw new UserMobileAlreadyExistException();
			}
		}
		userEntity = userDtoToEntity(userDto, new UserEntity());
		UserEntity referByUserEntity = userEntityRepository.findByReferenceCode(userDto.getReferralCode());
		if(referByUserEntity == null){
			throw new DataNotExistException();
		}
		userEntity.setReferenceBy(referByUserEntity.getId());
		/*String referralCode = null;
		long countByReferenceCode = 1;
		while(countByReferenceCode != 0){
			 referralCode =	GeneralUtil.generateReferralCode().toString();
			 countByReferenceCode = userEntityRepository.countByReferenceCode(referralCode);
		}
		userEntity.setReferenceCode(referralCode);
//		final String userReferralCode = referralCode;
		*/
		userEntityRepository.saveAndFlush(userEntity);
		Thread thread = new Thread(new Runnable() {

		@Override
		public void run() {
			
			/*if(userId != null){
				String referralCode = null;
				long countByReferenceCode = 1;
				while(countByReferenceCode !=0){
					 referralCode =	GeneralUtil.generateReferralCode().toString();
					 countByReferenceCode = userEntityRepository.countByReferenceCode(referralCode);
				}
				userEntityRepository.updateByIdAndReferralCode(userId, referralCode);
			
			}*/
			
			try {
				smsService.sendSMS(userDto.getMobile(), MailSmsContents.AddUser.getWelcomeSmsBody(userDto.getName(), userDto.getEmail()));
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
			EmailDto emailDto = new EmailDto();
			emailDto.setTo(new String[]{userDto.getEmail()});
			emailDto.setMailBody(MailSmsContents.AddUser.getMailBody(userDto.getName(), userDto.getEmail(), userDto.getMobile(), userDto.getPassword()));
			emailDto.setSubject(MailSmsContents.AddUser.MAILSUBJECT);
			try {
				emailService.sendMail(emailDto);
			} catch (MessagingException e) {
				 
				LOGGER.error(e.getMessage(), e);
			}
			
		}
	});

	thread.start();
	}
	
	@Override
	@Transactional(timeout = 100000, value = "transactionManager")
	public void addRestUser(final RestUserDto userDto) throws UserAlreadyExistException, UserMobileAlreadyExistException, UserEmailAlreadyExistException, DataNotExistException {
		UserEntity userEntity =userEntityRepository.findByUsernameOrMobile(userDto.getEmail(), userDto.getMobile());
		if (userEntity != null) {
			if(userEntity.getUsername().equalsIgnoreCase(userDto.getEmail()) && userEntity.getMobile().equalsIgnoreCase(userDto.getMobile()))
			{
				throw new UserAlreadyExistException();
			} else if((userEntity.getUsername().equalsIgnoreCase(userDto.getEmail()))){
				throw new UserEmailAlreadyExistException();
			} else if((userEntity.getMobile().equalsIgnoreCase(userDto.getMobile()))){
				throw new UserMobileAlreadyExistException();
			}
		}
		userEntity = restUserDtoToEntity(userDto, new UserEntity());
		UserEntity referByUserEntity = userEntityRepository.findByReferenceCode(userDto.getReferralCode());
		if(referByUserEntity == null){
			throw new DataNotExistException();
		}
		userEntity.setReferenceBy(referByUserEntity.getId());
		/*String referralCode = null;
		long countByReferenceCode = 1;
		while(countByReferenceCode != 0){
			 referralCode =	GeneralUtil.generateReferralCode().toString();
			 countByReferenceCode = userEntityRepository.countByReferenceCode(referralCode);
		}
		userEntity.setReferenceCode(referralCode);
//		final String userReferralCode = referralCode;
		*/
		userEntityRepository.saveAndFlush(userEntity);
		Thread thread = new Thread(new Runnable() {

		@Override
		public void run() {
			
			/*if(userId != null){
				String referralCode = null;
				long countByReferenceCode = 1;
				while(countByReferenceCode !=0){
					 referralCode =	GeneralUtil.generateReferralCode().toString();
					 countByReferenceCode = userEntityRepository.countByReferenceCode(referralCode);
				}
				userEntityRepository.updateByIdAndReferralCode(userId, referralCode);
			
			}*/
			try {
				smsService.sendSMS(userDto.getMobile(), MailSmsContents.AddUser.getWelcomeSmsBody(userDto.getName(), userDto.getEmail()));
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
			
			EmailDto emailDto = new EmailDto();
			emailDto.setTo(new String[]{userDto.getEmail()});
			emailDto.setMailBody(MailSmsContents.AddUser.getMailBody(userDto.getName(), userDto.getEmail(), userDto.getMobile(), userDto.getPassword()));
			emailDto.setSubject(MailSmsContents.AddUser.MAILSUBJECT);
			try {
				emailService.sendMail(emailDto);
			} catch (MessagingException e) {
				 
				LOGGER.error(e.getMessage(), e);
			}
			
		}
	});

	thread.start();
	}
	private UserEntity restUserDtoToEntity(RestUserDto dto, UserEntity entity) {
		if (entity == null) {
			entity = new UserEntity();
		}
		if(entity.getRole() == null){
			entity.setRole(roleEntityRepository.findOne(dto.getRole()));
		}
		if (dto.getId() != null && dto.getId() != 0) {
		    entity.setId(dto.getId());
		}
		entity.setId(dto.getId());
		if (dto.getPassword() != null) {
			entity.setPassword(PasswordUtility.encodePassword(dto.getPassword()));
		}
		if (dto.getName() != null) {
			entity.setName(dto.getName());
		}
		entity.setUsername(dto.getEmail());
		entity.setMobile(dto.getMobile());
		if(dto.getAddress() != null){
			entity.setAddress(dto.getAddress());
		}
		entity.setDeleteUser(false);
		entity.setValid(true);
		entity.setClient(clientRepository.findOne(ApplicationConstants.Clients.GGN));
		entity.setWorkspace(workspaceRepositiry.findOne(ApplicationConstants.Workspaces.GGN));
		return entity;
	}

	private UserEntity userDtoToEntity(UserDto dto, UserEntity entity) {
		if (entity == null) {
			entity = new UserEntity();
		}
		if(entity.getRole() == null){
			entity.setRole(roleEntityRepository.findOne(dto.getRole()));
		}
		if (dto.getId() != null && dto.getId() != 0) {
		    entity.setId(dto.getId());
		}
		entity.setId(dto.getId());
		if (dto.getPassword() != null) {
			entity.setPassword(PasswordUtility.encodePassword(dto.getPassword()));
		}
		if (dto.getName() != null) {
			entity.setName(dto.getName());
		}
		entity.setUsername(dto.getEmail());
		entity.setMobile(dto.getMobile());
		if(dto.getAddress() != null){
			entity.setAddress(dto.getAddress());
		}
		entity.setDeleteUser(false);
		entity.setValid(true);
		entity.setClient(clientRepository.findOne(ApplicationConstants.Clients.GGN));
		entity.setWorkspace(workspaceRepositiry.findOne(ApplicationConstants.Workspaces.GGN));
		return entity;
	}

	@Override
	@Transactional(timeout = 100000, value = "transactionManager", readOnly = true)
	  public List<UserDto> getUserList(String userType, int pageNum, int limit) {
		int offset = pageNum - 1;
	    Pageable pageable = new PageRequest(offset, limit);
	    /*if (StringUtils.isEmpty(userType) || "workspaceUser".equals(userType)) {
	      return userEntityToDto(userEntityRepository.findAllByWorkspaceIdPageable(GeneralUtil.getWorkspaceId(), ApplicationConstants.Roles.SITE_ADMIN,
	          false, pageable));
	    } else if ("manualUser".equals(userType)) {
	      return userEntityToDto(userEntityRepository.findByRoleIdAndAdUserPageable(ApplicationConstants.Roles.SITE_ADMIN, false, false, pageable));
	    } else if ("adUser".equals(userType)) {
	      return userEntityToDto(userEntityRepository.findByRoleIdAndAdUserPageable(ApplicationConstants.Roles.SITE_ADMIN, true, false, pageable));
	    } else {*/
	      return userEntityToDto(userEntityRepository.findByRoleIdPageable(ApplicationConstants.Roles.DELIVERY_ADMIN, false, pageable));
	   /* }*/

	  }
	
	private List<UserDto> userEntityToDto(List<UserEntity> userEntityList) {
	    if (userEntityList == null || userEntityList.isEmpty()) {
	      return new ArrayList<UserDto>();
	    } else {
	      List<UserDto> dtoList = new ArrayList<UserDto>();
	      for (UserEntity entity : userEntityList) {
	        UserDto dto = new UserDto();
	        entityToDto(entity, dto);
	        dtoList.add(dto);
	      }
	      return dtoList;
	    }
	  }
	
	@Override
	  public UserDto entityToDto(UserEntity entity, UserDto dto) {

	    dto.setMobile(entity.getMobile());
	    dto.setEmail(entity.getUsername());
	    dto.setName(entity.getName());
	    dto.setRole(entity.getRole().getId());
	    dto.setId(entity.getId());
	    dto.setValid(entity.isValid());
	    dto.setAddress(entity.getAddress());
	    /*  WorkspaceEntity workspace = entity.getWorkspace();
	    if(workspace != null){
	        dto.setAdminForWorkspaceName(workspace.getName());
	    }
	    ClientEntity client = entity.getClient();
	    if(client != null){
	        dto.setAdminForClientName(client.getName());
	    }*/
	    dto.setLoginInfo(entity.getLoginInfo());
	    return dto;
	  }
	  private boolean checkUniqueUserEmailBeforeUpdate(String emailId, Integer id) {

		    boolean isEmailUnique = true;

		    UserEntity user = userEntityRepository.findByUsernameAndIdNot(emailId, id);
		    if (null != user) {
		      isEmailUnique = false;
		    }
		    return isEmailUnique;

		  }
	  
	  private boolean checkUniqueUserMobileBeforeUpdate(String emailId, Integer id) {

		    boolean isEmailUnique = true;

		    UserEntity user = userEntityRepository.findByMobileAndIdNot(emailId, id);
		    if (null != user) {
		      isEmailUnique = false;
		    }
		    return isEmailUnique;

		  }

	@Transactional(timeout = 100000, value = "transactionManager")
	  public void editUser(UserDto userDto) throws UserAlreadyExistException, UserCanNotRemoveFromPoolException {
	    
	    String email = userDto.getEmail();
	    String mobile = userDto.getMobile();
	    Integer id = userDto.getId();

	    if (!checkUniqueUserEmailBeforeUpdate(email, id)) {
	      throw new UserAlreadyExistException();
	    }
	    if (!checkUniqueUserMobileBeforeUpdate(email, id)) {
	    	throw new UserAlreadyExistException();
	    }
	    

	    UserEntity oldUserEntity = userEntityRepository.findOne(userDto.getId());
	    
	    if (!userDto.isValid()) {
	        /*if( oldUserEntity.getWorkspace() != null){
	          long size = userEntityRepository.countByWorkspaceAndDeleteUser(oldUserEntity.getWorkspace(), false);
	            if (size == 1L) {
	                throw new UserHaveSingleWorkspaceException("edituser.singleworkspaceerror");
	            }
	        }else if(oldUserEntity.getClient() != null){
	          long size = userEntityRepository.countByClientAndDeleteUser(oldUserEntity.getWorkspace(), false);
	            if (size == 1L) {
	                throw new UserHaveSingleWorkspaceException("edituser.singleclienterror");
	            }
	        }*/
	        oldUserEntity.getWorkspaceList().clear();
	        oldUserEntity.setWorkspace(null);
	        oldUserEntity.setClient(null);
	        userDto.setRole(ApplicationConstants.Roles.USER);
	        securityUtil.logoutManully(id);
	        
	    }else{
	        Integer clientId2 = GeneralUtil.getClientId();
	        Integer workspaceId = GeneralUtil.getWorkspaceId();
	        WorkspaceEntity workspace = oldUserEntity.getWorkspace();
	        if(clientId2 != null){
	            oldUserEntity.getWorkspaceList().clear();
	            
//	            List<Integer> workspaceList = userDto.getWorkspaceList();
	            /*if(workspace!=null && (workspaceList==null||!workspaceList.contains(workspace.getId()))){
	                //workspace admin should always be workspace user. we can not remove admin frm workspace pool 
	                throw new UserCanNotRemoveFromPoolException(workspace.getName(), "workspace");
	            }
	            if(workspaceList != null){
	                for (Integer workspaceId3 : workspaceList) {
	                    WorkspaceEntity workspaceEntity = workspaceRepositiry.getOne(workspaceId3);
	                    oldUserEntity.getWorkspaceList().add(workspaceEntity);
	                }
	            }*/
	        }/*else if(workspaceId != null){
	            boolean availableInCurrentWorkspace = userDto.isAvailableInCurrentWorkspace();
	            long count = userEntityRepository.countByUserIdAndWorkspaceId(id, workspaceId);
	            if(availableInCurrentWorkspace){
	                if(count==0L){
	                    WorkspaceEntity workspaceEntity = workspaceRepositiry.getOne(workspaceId);
	                    oldUserEntity.getWorkspaceList().add(workspaceEntity);
	                }
	            }else if(workspace!=null && workspace.getId()==workspaceId){
	                //workspace admin should always be workspace user. we can not remove admin frm workspace pool 
	                throw new UserCanNotRemoveFromPoolException(workspace.getName(), "workspace");
	            }else if(count!=0L){
	                WorkspaceEntity workspaceEntity = workspaceRepositiry.getOne(workspaceId);
	                oldUserEntity.getWorkspaceList().remove(workspaceEntity);
	            }
	        }
	        */
	    }
	    
	    oldUserEntity.setMobile(userDto.getMobile());
	    oldUserEntity.setName(userDto.getName());
	    oldUserEntity.setRole(roleEntityRepository.getOne(userDto.getRole()));
	    oldUserEntity.setValid(userDto.isValid());
	    userEntityRepository.save(oldUserEntity);

	  }

	@Override
	public List<NameIdDto> searchUser(String userType, String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getCount(int maxResult, String userType) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public UserDto getUserById(int id, boolean isSiteAdmin, boolean isClientAdmin, boolean isWorkspaceAdmin) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(timeout = 100000, value = "transactionManager", readOnly = true)
	public List<UserDto> getUserChainTree() {
		List<UserEntity> findAllByReferenceByIsNull = userEntityRepository.findAllByReferenceByIsNull();
		return null;
	}

	

}
