package com.auth;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

import com.dto.CurrentUserDto;
import com.service.IUserEntityService;
import com.utility.Tenant;

public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider {
  private static final Logger LOGGER = LogManager.getLogger(CustomDaoAuthenticationProvider.class);


  private @Autowired HttpServletRequest request;
  @Autowired
  private IUserEntityService userEntityService;


  @Override
  protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
      throws AuthenticationException {
    Object salt = null;

    // if (this.saltSource != null) {
    // salt = this.saltSource.getSalt(userDetails);
    // }

    CurrentUserDto currentUserDto = (CurrentUserDto) userDetails;
    if (this.saltSource != null) {
      salt = this.saltSource.getSalt(userDetails);
    }

    if (authentication.getCredentials() == null) {
      logger.debug("Authentication failed: no credentials provided");

      throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
    }
//    if(currentUserDto.getRoleId()!=1){
//    	throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
//    }
    String presentedPassword = authentication.getCredentials().toString();

    if (!passwordEncoder.isPasswordValid(userDetails.getPassword(), presentedPassword, salt)) {
	logger.debug("Authentication failed: password does not match stored value");

	throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
    }
  }

  protected final UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
   /*
    Tenant.setCurrentTenant(tocCode);
*/    CurrentUserDto loadedUser = null;
    try {
      loadedUser = (CurrentUserDto) this.getUserDetailsService().loadUserByUsername(username);
    } catch (UsernameNotFoundException notFound) {
        throw notFound;
    } catch (Exception repositoryProblem) {
      throw repositoryProblem;
    } finally {
      Tenant.clearCurrentTenant();
    }

    return loadedUser;
  }


  private void setPasswordEncoder(PasswordEncoder passwordEncoder) {
    Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");

    this.passwordEncoder = passwordEncoder;
  }
}
