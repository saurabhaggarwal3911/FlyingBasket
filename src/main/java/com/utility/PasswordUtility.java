package com.utility;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtility {
  private PasswordUtility() {}

  public static String encodePassword(String password) {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    return passwordEncoder.encode(password);
  }

  public static boolean matchesPassword(String password, String dbPassword) {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    return passwordEncoder.matches(password, dbPassword);
  }

  public static class SessionIdentifierGenerator {
    private SecureRandom secureRandom = new SecureRandom();

    public String nextSessionId() {
      return new BigInteger(20, secureRandom).toString();
    }
  }
}
