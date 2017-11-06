package com.utility;


public interface ApplicationConstants {
	String APP_VERSION = "1.0";
	String FILE_VERSION = "1.0";
	String GROCERY = "grocery";
	String APP_NAME = "Flying Basket";
	public static final String DOWNLOAD_FILES_BACKUP_FOLDER = "C:\\flyingbasket-fb\\staticdata\\FileBackUp\\";
	public static final String UPLOADED_FILES_FOLDER = "documents";
	String DOMAIN_URL_FOR_FILEDOWNLOAD = "domainURLForFileDownload";
	String SUCCESS = "success";
	public static final String OUT_OF_STOCK = "Out of stock";
	String ISO_8859_1 = "ISO-8859-1";
	
	public interface ApplicationConfigurationConstants{
		String HOST = "HOST";
		String PORT = "PORT";
		String USERNAME = "USERNAME";
		String PASSWORD = "PASSWORD";
		String FROM = "FROM";
		String TLS_ENABLED = "TLS_ENABLED";
		String AUTH = "AUTH";
	}
	
	 public interface Roles {
		 public static int SITE_ADMIN = 1;
		 public static int CLIENT_ADMIN = 2;
		 public static int WORKSPACE_ADMIN = 3;
		 public static int DELIVERY_ADMIN = 4;
		 public static int USER = 5;
	 }
	 public interface Workspaces {
		 public static int GGN = 1;
	 }
	 public interface Clients {
		 public static int GGN = 1;
	 }
	 public interface MembershipType {
		 public static String LOYALITY = "Loyality";
		 public static String GOLD = "Gold";
		 public static String SILVER = "Silver";
		 public static String PLATINUM = "Platinum";
	 }
	 public interface AccessTypes {
		 public static String ANDROID = "Android";
		 public static String IPHONE = "iPhone";
		 public static String DESKTOP = "Desktop";
		 public static String HT = "HT";
	 }


}
