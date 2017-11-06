package com.utility;

public class Tenant {
  private static final ThreadLocal<String> DBTENANT = new ThreadLocal<>();

  private Tenant() {}

  public static void setCurrentTenant(String tenant) {
    DBTENANT.set(tenant);
  }

  public static String getCurrentTenant() {
    return DBTENANT.get();
  }

  public static void clearCurrentTenant() {
    DBTENANT.remove();
  }
}
