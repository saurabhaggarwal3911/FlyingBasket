package com.utility;



import java.security.SecureRandom;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;

import com.dto.CurrentUserDto;


public class GeneralUtil {
  private static final Logger LOGGER = LogManager.getLogger(GeneralUtil.class);

  private GeneralUtil() {}
  
  public static Integer getUserId() {
      CurrentUserDto currentUserDto = (CurrentUserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      return currentUserDto.getId();
    }

  public static Integer getWorkspaceId() {
    CurrentUserDto currentUserDto = (CurrentUserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return currentUserDto.getWorkspaceId();
  }


  public static Integer getClientId() {
    CurrentUserDto currentUserDto = (CurrentUserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return currentUserDto.getClientId();
  }

  public static int getRoleId() {
    CurrentUserDto currentUserDto = (CurrentUserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return currentUserDto.getRoleId();
  }

  public static String getDate() {
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    return sdf.format(date);
  }

  public static Date getDateFromString(String date) {
      SimpleDateFormat sdf = new SimpleDateFormat("DD-MM-YYYY HH:mm");
      try {
          if(date!=null&&!date.isEmpty()){
        return sdf.parse(date);
          }else{
              return null;   
          }
    } catch (ParseException e) {
        return null;
    }
    }
  public static Date getDateFromTime(String time) {
    try {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
      Date date = simpleDateFormat.parse(time);
      Calendar c1 = Calendar.getInstance();
      c1.setTime(date);
      Calendar calendar = Calendar.getInstance();
      calendar.set(Calendar.HOUR_OF_DAY, c1.get(Calendar.HOUR_OF_DAY));
      calendar.set(Calendar.MINUTE, c1.get(Calendar.MINUTE));
      return calendar.getTime();
    } catch (ParseException e) {
      LOGGER.error(e.getMessage(), e);
    }
    return null;
  }

  public static String getCurrentDay() {
    Calendar calendar = Calendar.getInstance();
    return new SimpleDateFormat("EEEE", Locale.ENGLISH).format(calendar.getTime());
  }

  public static String getCurrentMonth() {
    Calendar calendar = Calendar.getInstance();
    return new SimpleDateFormat("MMMM", Locale.ENGLISH).format(calendar.getTime());
  }

  public static int getCurrentDate() {
    Calendar calendar = Calendar.getInstance();
    return calendar.get(Calendar.DAY_OF_MONTH);
  }
  public static int getCurrentMonthNum() {
	  Calendar calendar = Calendar.getInstance();
	  return calendar.get(Calendar.MONTH);
  }

  public static int getDateOfMonth(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar.get(Calendar.DAY_OF_MONTH);
  }

  public static int getMonth(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar.get(Calendar.MONTH);
  }

  public static Date addMonth(Date date, int month) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.MONTH, month);
    return calendar.getTime();
  }

  public static boolean isDateToday(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    Calendar tcalendar = Calendar.getInstance();
    return tcalendar.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)
        && tcalendar.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) && tcalendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR);
  }

  public static String getSharingTimeInString(Date startTime) {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    return sdf.format(startTime);
  }

  public static void printMemory() {
    Runtime runtime = Runtime.getRuntime();
    final NumberFormat format = NumberFormat.getInstance();
    final long maxMemory = runtime.maxMemory();
    final long allocatedMemory = runtime.totalMemory();
    final long freeMemory = runtime.freeMemory();
    final long mb = 1024 * 1024;
    final String mega = " MB";
    LOGGER.info("========================== Memory Info ==========================");
    LOGGER.info("Total Thread:  " + Thread.activeCount());
    LOGGER.info("Free memory: " + format.format(freeMemory / mb) + mega);
    LOGGER.info("Allocated memory: " + format.format(allocatedMemory / mb) + mega);
    LOGGER.info("Max memory: " + format.format(maxMemory / mb) + mega);
    LOGGER.info("Total free memory: " + format.format((freeMemory + (maxMemory - allocatedMemory)) / mb) + mega);
    LOGGER.info("=================================================================\n");

  }

  public static StringBuilder generateReferralCode() {
	  char[] chars = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ".toUpperCase().toCharArray();
	  StringBuilder sb = new StringBuilder();
	  Random random = new SecureRandom();
	  for (int i = 0; i < 7; i++) {
		  char c = chars[random.nextInt(chars.length)];
		  sb.append(c);
	  }
	  return sb;	
  }
  public static boolean isParsable(String input){
	  boolean parsable = true;
	  try{
		  Integer.parseInt(input);
	  }catch(NumberFormatException e){
		  parsable = false;
	  }
	  return parsable;
  }
}
