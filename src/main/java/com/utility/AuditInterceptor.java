package com.utility;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.dto.CurrentUserDto;
import com.entity.BaseEntity;
import com.entity.WorkspaceBaseEntity;

public class AuditInterceptor extends EmptyInterceptor {

  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
    if (entity instanceof BaseEntity) {
      // BaseEntity entity2 = (BaseEntity) entity;
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (authentication != null && authentication.getPrincipal() instanceof CurrentUserDto) {
        CurrentUserDto currentUserDto = (CurrentUserDto) authentication.getPrincipal();
        List<String> propertyList = Arrays.asList(propertyNames);
        int modifiedByIndexOf = propertyList.indexOf("modifiedBy");
        int modifiedOnIndexOf = propertyList.indexOf("modifiedOn");
        currentState[modifiedByIndexOf] = currentUserDto.getId();
        currentState[modifiedOnIndexOf] = new Date();
        return true;
      }

    }
    return false;
  }

  public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
    if (entity instanceof BaseEntity) {
      // BaseEntity entity2 = (BaseEntity) entity;
    	List<String> propertyList = Arrays.asList(propertyNames);
    	int createdOnIndexOf = propertyList.indexOf("createdOn");
    	state[createdOnIndexOf] = new Date();
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if (authentication != null && authentication.getPrincipal() instanceof CurrentUserDto) {
    		CurrentUserDto currentUserDto = (CurrentUserDto) authentication.getPrincipal();
    		int createdByIndexOf = propertyList.indexOf("createdBy");
    		state[createdByIndexOf] = currentUserDto.getId();
    		if (entity instanceof WorkspaceBaseEntity) {
    			int workspaceIdIndexOf = propertyList.indexOf("workspaceId");
    			if (currentUserDto.getWorkspaceId() != null) {
    				state[workspaceIdIndexOf] = currentUserDto.getWorkspaceId();
    			}
    		}
    		return true;
    	}

    }
    return false;
  }

}
