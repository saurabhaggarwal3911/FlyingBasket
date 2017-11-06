package com.auth;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;

import com.dto.CurrentUserDto;

@Component
public class SecurityUtil {
  private static final Logger LOGGER = LogManager.getLogger(SecurityUtil.class);
  @Autowired
  private SessionRegistry sessionRegistry;

  public void logoutManully(int id) {
    List<Object> loggedUsers = sessionRegistry.getAllPrincipals();
    for (Object principal : loggedUsers) {
      if (principal instanceof CurrentUserDto) {
        final CurrentUserDto loggedUser = (CurrentUserDto) principal;
        if (loggedUser.getId() == id) {

          List<SessionInformation> sessionsInfo = sessionRegistry.getAllSessions(principal, false);
          if (null != sessionsInfo && !sessionsInfo.isEmpty()) {
            for (SessionInformation sessionInformation : sessionsInfo) {
              LOGGER.info("Exprire now :" + sessionInformation.getSessionId());
              sessionInformation.expireNow();
            }
          }
        }
      }
    }
  }
}
