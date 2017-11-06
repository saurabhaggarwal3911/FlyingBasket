package com.repository;

import java.util.List;

import org.apache.catalina.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.entity.UserEntity;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Integer>, UserEntityRepositoryCustom {
    /*@EntityGraph(value = "userWithRole", type = EntityGraphType.LOAD)OSS
    public UserEntity findOneByUsername(String username);*/

    @Query("select fetchInfo from UserEntity as fetchInfo where fetchInfo.username = ?1 or fetchInfo.mobile =?1")
    public UserEntity findByUsername(String email);
    @Query("select fetchInfo from UserEntity as fetchInfo where fetchInfo.username = ?1 or fetchInfo.mobile =?2")
    public UserEntity findByUsernameOrMobile(String email, String mobile);

    public UserEntity findByUsernameAndIdNot(String emailId, Integer id);

    public List<UserEntity> findByDeleteUserIsTrue();

/*
    @Query("select distinct(fetchInfo) from UserEntity as fetchInfo where fetchInfo.role.id != ?1 and fetchInfo.deleteUser =?2 order by fetchInfo.name asc")
    public List<UserEntity> findByNotRoleIdPageable(Integer siteAdmin, boolean deleteValue, Pageable pageable);
*/

    @Query("select distinct(fetchInfo) from UserEntity as fetchInfo where fetchInfo.role.id != ?1 and fetchInfo.deleteUser =?2 order by fetchInfo.name asc")
    public List<UserEntity> findByRoleIdPageable(Integer siteAdmin, boolean deleteValue, Pageable pageable);

    @Query("select count(fetchInfo) from UserEntity as fetchInfo where fetchInfo.role.id != ?1 and fetchInfo.deleteUser =?2")
    public long countByRoleIdPageable(Integer siteAdmin, boolean deleteValue);
    
    @Query("select  distinct new UserEntity(fetchInfo.id, fetchInfo.name) from UserEntity as fetchInfo where fetchInfo.role.id != ?1 and fetchInfo.deleteUser =?2 and (fetchInfo.name like ?3) order by fetchInfo.name asc")
    public List<UserEntity> findIdNameByRoleIdAndNameLike(Integer roleId, boolean deleteValue, String username1, Pageable pageable);
  
    @Query("select distinct(fetchInfo) from UserEntity as fetchInfo JOIN fetchInfo.workspaceList as mapping "
    	      + " where fetchInfo.role.id !=?2 and mapping.id = ?1 and fetchInfo.deleteUser =?3 order by fetchInfo.name asc")
	public List<UserEntity> findAllByWorkspaceIdPageable(Integer workspaceId, int siteAdmin, boolean b,
			Pageable pageable);
	public UserEntity findByMobileAndIdNot(String mobile, Integer id);
	
	
	@Modifying
    @Query(value = "update UserEntity u set u.referenceCode=?2 where u.id=?1")
	public void updateByIdAndReferenceCode(Integer userId, String referenceCode);
	
    @Query("select  distinct new UserEntity(fetchInfo.id, fetchInfo.name) from UserEntity as fetchInfo where fetchInfo.referenceCode = ?1 and fetchInfo.deleteUser = false and fetchInfo.valid = true")
	public UserEntity findByReferenceCode(String referenceCode);
    @Query("select count(fetchInfo) from UserEntity as fetchInfo where fetchInfo.referenceBy= ?1 and fetchInfo.referenceCode is not null")
	public long countByReferenceByAndReferralCodeIsNotNull(int referenceBy);
	@Query("select count(fetchInfo) from UserEntity as fetchInfo where fetchInfo.referenceCode=?1")
	public long countByReferenceCode(String referralCode);
	
	public UserEntity findById(Integer id);
	public List<UserEntity> findAllByReferenceByIsNull();

   
}
