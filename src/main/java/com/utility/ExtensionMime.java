package com.utility;

import java.util.HashMap;
import java.util.Map;

public interface ExtensionMime {
  public static final String SEVEN_Z = "7z";
  public static final String PDF = "pdf";
  public static final String APK = "apk";
  public static final String MPKG = "mpKG";
  public static final String BMP = "bmp";
  public static final String COD = "cod";
  public static final String JAD = "jad";
  public static final String JAR = "jar";
  public static final String ZIP = "zip";
  public static final String RAR = "rar";
  public static final String DOCX = "docx";
  public static final String IPA = "ipa";
  public static final String PLIST = "plist";
  public static final String DOC = "doc";
  public static final String EXE = "exe";
  public static final String XLS = "xls";
  public static final String XLSX = "xlsx";
  public static final String PPTX = "pptx";
  public static final String PPT = "ppt";
  public static final String PPS = "pps";
  public static final String GIF = "gif";
  public static final String JPG = "jpg";
  public static final String PNG = "png";


  Map<String, String> TYPE = new HashMap<String, String>() {
    {
      put(SEVEN_Z, "application/x-7z-compressed");
      put(PDF, "application/pdf");
      put(APK, "application/vnd.android.package-archive");
      put(MPKG, "application/vnd.apple.installer+xml");
      put(BMP, "image/bmp");
      put(COD, "application/vnd.rim.cod");
      put(JAD, "text/vnd.sun.j2me.app-descriptor");
      put(JAR, "application/java-archive");
      put(ZIP, "application/zip");
      put(RAR, "application/x-rar-compressed");
      put(DOCX, "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
      put(DOC, "application/msword");
      put(XLS, "application/vnd.ms-excel");
    }
  };
}
