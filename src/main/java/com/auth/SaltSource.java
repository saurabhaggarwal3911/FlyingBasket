package com.auth;

import org.springframework.security.core.userdetails.UserDetails;


/**
 * Provides alternative sources of the salt to use for encoding passwords.
 *
 * @author Ben Alex
 */
public interface SaltSource {
  // ~ Methods
  // ========================================================================================================

  /**
   * Returns the salt to use for the indicated user.
   *
   * @param user from the <code>AuthenticationDao</code>
   *
   * @return the salt to use for this <code>UserDetails</code>
   */
  Object getSalt(UserDetails user);
}
