package com.utility;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.dto.ApplicationConfigurationDto;
import com.service.IApplicationConfigurationService;

@Component
public class ServletContextInjector implements ServletContextAware, InitializingBean {

  private ServletContext servletContext;
  @Autowired
  private IApplicationConfigurationService applicationService;

  @Override
  public void setServletContext(ServletContext servletContext) {
    // TODO Auto-generated method stub
    this.servletContext = servletContext;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    // TODO Auto-generated method stub
    List<ApplicationConfigurationDto> allSetting = applicationService.getAllSetting();
    for (ApplicationConfigurationDto fgraApplicationSettings : allSetting) {
      if (fgraApplicationSettings.getName().equals(ApplicationConfigurationEnum.SMTP_AUTHENTICATION.toString())) {
        servletContext.setAttribute(ApplicationConfigurationEnum.SMTP_AUTHENTICATION.toString(), fgraApplicationSettings.getValue());
      } else if (fgraApplicationSettings.getName().equals(ApplicationConfigurationEnum.SMTP_HOST.toString())) {
        servletContext.setAttribute(ApplicationConfigurationEnum.SMTP_HOST.toString(), fgraApplicationSettings.getValue());
      } else if (fgraApplicationSettings.getName().equals(ApplicationConfigurationEnum.SMTP_PASSWORD.toString())) {
        servletContext.setAttribute(ApplicationConfigurationEnum.SMTP_PASSWORD.toString(), fgraApplicationSettings.getValue());
      } else if (fgraApplicationSettings.getName().equals(ApplicationConfigurationEnum.SMTP_PORT.toString())) {
        servletContext.setAttribute(ApplicationConfigurationEnum.SMTP_PORT.toString(), fgraApplicationSettings.getValue());
      } else if (fgraApplicationSettings.getName().equals(ApplicationConfigurationEnum.SMTP_TLS_ENABLED.toString())) {
        servletContext.setAttribute(ApplicationConfigurationEnum.SMTP_TLS_ENABLED.toString(), fgraApplicationSettings.getValue());
      } else if (fgraApplicationSettings.getName().equals(ApplicationConfigurationEnum.SMTP_USERNAME.toString())) {
        servletContext.setAttribute(ApplicationConfigurationEnum.SMTP_USERNAME.toString(), fgraApplicationSettings.getValue());
      } else if (fgraApplicationSettings.getName().equals(ApplicationConfigurationEnum.SMTP_EMAIL_EXEC.toString())) {
        servletContext.setAttribute(ApplicationConfigurationEnum.SMTP_EMAIL_EXEC.toString(), fgraApplicationSettings.getValue());
      } else if (fgraApplicationSettings.getName().equals(ApplicationConfigurationEnum.ANDROID_DOWNLOAD_URL.toString())) {
        servletContext.setAttribute(ApplicationConfigurationEnum.ANDROID_DOWNLOAD_URL.toString(), fgraApplicationSettings.getValue());
      } else if (fgraApplicationSettings.getName().equals(ApplicationConfigurationEnum.ANDROID_VERSION.toString())) {
        servletContext.setAttribute(ApplicationConfigurationEnum.ANDROID_VERSION.toString(), fgraApplicationSettings.getValue());
      } else if (fgraApplicationSettings.getName().equals(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())) {
        servletContext.setAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString(), fgraApplicationSettings.getValue());
      } else if (fgraApplicationSettings.getName().equals(ApplicationConfigurationEnum.HT_VERSION.toString())) {
        servletContext.setAttribute(ApplicationConfigurationEnum.HT_VERSION.toString(), fgraApplicationSettings.getValue());
      } else if (fgraApplicationSettings.getName().equals(ApplicationConfigurationEnum.ALLOW_ALERT_NOTIFICATION.toString())) {
        servletContext.setAttribute(ApplicationConfigurationEnum.ALLOW_ALERT_NOTIFICATION.toString(), fgraApplicationSettings.getValue());
      } else if (fgraApplicationSettings.getName().equals(ApplicationConfigurationEnum.APP_NAME.toString())) {
        servletContext.setAttribute(ApplicationConfigurationEnum.APP_NAME.toString(), fgraApplicationSettings.getValue());
      } else if (fgraApplicationSettings.getName().equals(ApplicationConfigurationEnum.APP_URL.toString())) {
        servletContext.setAttribute(ApplicationConfigurationEnum.APP_URL.toString(), fgraApplicationSettings.getValue());
      } else if (fgraApplicationSettings.getName().equals(ApplicationConfigurationEnum.ALLOW_MAIL_NOTIFICATION.toString())) {
          servletContext.setAttribute(ApplicationConfigurationEnum.ALLOW_MAIL_NOTIFICATION.toString(), fgraApplicationSettings.getValue());
      } else if (fgraApplicationSettings.getName().equals(ApplicationConfigurationEnum.PUBLIC_CONTENT.toString())) {
    	  servletContext.setAttribute(ApplicationConfigurationEnum.PUBLIC_CONTENT.toString(), fgraApplicationSettings.getValue());
      } else if (fgraApplicationSettings.getName().equals(ApplicationConfigurationEnum.SMS_URL.toString())) {
    	  servletContext.setAttribute(ApplicationConfigurationEnum.SMS_URL.toString(), fgraApplicationSettings.getValue());
      } else if (fgraApplicationSettings.getName().equals(ApplicationConfigurationEnum.SMS_USERNAME.toString())) {
    	  servletContext.setAttribute(ApplicationConfigurationEnum.SMS_USERNAME.toString(), fgraApplicationSettings.getValue());
      } else if (fgraApplicationSettings.getName().equals(ApplicationConfigurationEnum.SMS_PASSWORD.toString())) {
    	  servletContext.setAttribute(ApplicationConfigurationEnum.SMS_PASSWORD.toString(), fgraApplicationSettings.getValue());
      } else if (fgraApplicationSettings.getName().equals(ApplicationConfigurationEnum.SMS_COST_CENTER_ID.toString())) {
    	  servletContext.setAttribute(ApplicationConfigurationEnum.SMS_COST_CENTER_ID.toString(), fgraApplicationSettings.getValue());
      } else if (fgraApplicationSettings.getName().equals(ApplicationConfigurationEnum.SMS_APP_ID.toString())) {
    	  servletContext.setAttribute(ApplicationConfigurationEnum.SMS_APP_ID.toString(), fgraApplicationSettings.getValue());
      } else if (fgraApplicationSettings.getName().equals(ApplicationConfigurationEnum.SMS_FROM.toString())) {
    	  servletContext.setAttribute(ApplicationConfigurationEnum.SMS_FROM.toString(), fgraApplicationSettings.getValue());
      } else if (fgraApplicationSettings.getName().equals(ApplicationConfigurationEnum.SMS_TYPE_ID.toString())) {
    	  servletContext.setAttribute(ApplicationConfigurationEnum.SMS_TYPE_ID.toString(), fgraApplicationSettings.getValue());
      } else if (fgraApplicationSettings.getName().equals(ApplicationConfigurationEnum.SMS_DLR_ID.toString())) {
    	  servletContext.setAttribute(ApplicationConfigurationEnum.SMS_DLR_ID.toString(), fgraApplicationSettings.getValue());
      } else if (fgraApplicationSettings.getName().equals(ApplicationConfigurationEnum.ALLOW_SMS_NOTIFICATION.toString())) {
    	  servletContext.setAttribute(ApplicationConfigurationEnum.ALLOW_SMS_NOTIFICATION.toString(), fgraApplicationSettings.getValue());
      } 
    }
  }

  public void upateContext() {
    // TODO Auto-generated method stub
    List<ApplicationConfigurationDto> allSetting = applicationService.getAllSetting();
    for (ApplicationConfigurationDto applicationSettings : allSetting) {
      if (applicationSettings.getName().equals(ApplicationConfigurationEnum.SMTP_AUTHENTICATION.toString())) {
        servletContext.setAttribute(ApplicationConfigurationEnum.SMTP_AUTHENTICATION.toString(), applicationSettings.getValue());
      } else if (applicationSettings.getName().equals(ApplicationConfigurationEnum.SMTP_HOST.toString())) {
        servletContext.setAttribute(ApplicationConfigurationEnum.SMTP_HOST.toString(), applicationSettings.getValue());
      } else if (applicationSettings.getName().equals(ApplicationConfigurationEnum.SMTP_PASSWORD.toString())) {
        servletContext.setAttribute(ApplicationConfigurationEnum.SMTP_PASSWORD.toString(), applicationSettings.getValue());
      } else if (applicationSettings.getName().equals(ApplicationConfigurationEnum.SMTP_PORT.toString())) {
        servletContext.setAttribute(ApplicationConfigurationEnum.SMTP_PORT.toString(), applicationSettings.getValue());
      } else if (applicationSettings.getName().equals(ApplicationConfigurationEnum.SMTP_TLS_ENABLED.toString())) {
        servletContext.setAttribute(ApplicationConfigurationEnum.SMTP_TLS_ENABLED.toString(), applicationSettings.getValue());
      } else if (applicationSettings.getName().equals(ApplicationConfigurationEnum.SMTP_USERNAME.toString())) {
        servletContext.setAttribute(ApplicationConfigurationEnum.SMTP_USERNAME.toString(), applicationSettings.getValue());
      } else if (applicationSettings.getName().equals(ApplicationConfigurationEnum.SMTP_EMAIL_EXEC.toString())) {
        servletContext.setAttribute(ApplicationConfigurationEnum.SMTP_EMAIL_EXEC.toString(), applicationSettings.getValue());
      } else if (applicationSettings.getName().equals(ApplicationConfigurationEnum.ANDROID_DOWNLOAD_URL.toString())) {
        servletContext.setAttribute(ApplicationConfigurationEnum.ANDROID_DOWNLOAD_URL.toString(), applicationSettings.getValue());
      } else if (applicationSettings.getName().equals(ApplicationConfigurationEnum.ANDROID_VERSION.toString())) {
        servletContext.setAttribute(ApplicationConfigurationEnum.ANDROID_VERSION.toString(), applicationSettings.getValue());
      } else if (applicationSettings.getName().equals(ApplicationConfigurationEnum.HT_FILE_VERSION.toString())) {
        servletContext.setAttribute(ApplicationConfigurationEnum.HT_FILE_VERSION.toString(), applicationSettings.getValue());
      } else if (applicationSettings.getName().equals(ApplicationConfigurationEnum.HT_VERSION.toString())) {
        servletContext.setAttribute(ApplicationConfigurationEnum.HT_VERSION.toString(), applicationSettings.getValue());
      } else if (applicationSettings.getName().equals(ApplicationConfigurationEnum.ALLOW_ALERT_NOTIFICATION.toString())) {
        servletContext.setAttribute(ApplicationConfigurationEnum.ALLOW_ALERT_NOTIFICATION.toString(), applicationSettings.getValue());
      } else if (applicationSettings.getName().equals(ApplicationConfigurationEnum.APP_NAME.toString())) {
        servletContext.setAttribute(ApplicationConfigurationEnum.APP_NAME.toString(), applicationSettings.getValue());
      } else if (applicationSettings.getName().equals(ApplicationConfigurationEnum.APP_URL.toString())) {
        servletContext.setAttribute(ApplicationConfigurationEnum.APP_URL.toString(), applicationSettings.getValue());
      } else if (applicationSettings.getName().equals(ApplicationConfigurationEnum.ALLOW_MAIL_NOTIFICATION.toString())) {
            servletContext.setAttribute(ApplicationConfigurationEnum.ALLOW_MAIL_NOTIFICATION.toString(), applicationSettings.getValue());
      }else if (applicationSettings.getName().equals(ApplicationConfigurationEnum.PUBLIC_CONTENT.toString())) {
    	  servletContext.setAttribute(ApplicationConfigurationEnum.PUBLIC_CONTENT.toString(), applicationSettings.getValue());
      } else if (applicationSettings.getName().equals(ApplicationConfigurationEnum.SMS_URL.toString())) {
    	  servletContext.setAttribute(ApplicationConfigurationEnum.SMS_URL.toString(), applicationSettings.getValue());
      } else if (applicationSettings.getName().equals(ApplicationConfigurationEnum.SMS_USERNAME.toString())) {
    	  servletContext.setAttribute(ApplicationConfigurationEnum.SMS_USERNAME.toString(), applicationSettings.getValue());
      } else if (applicationSettings.getName().equals(ApplicationConfigurationEnum.SMS_PASSWORD.toString())) {
    	  servletContext.setAttribute(ApplicationConfigurationEnum.SMS_PASSWORD.toString(), applicationSettings.getValue());
      } else if (applicationSettings.getName().equals(ApplicationConfigurationEnum.SMS_COST_CENTER_ID.toString())) {
    	  servletContext.setAttribute(ApplicationConfigurationEnum.SMS_COST_CENTER_ID.toString(), applicationSettings.getValue());
      } else if (applicationSettings.getName().equals(ApplicationConfigurationEnum.SMS_APP_ID.toString())) {
    	  servletContext.setAttribute(ApplicationConfigurationEnum.SMS_APP_ID.toString(), applicationSettings.getValue());
      } else if (applicationSettings.getName().equals(ApplicationConfigurationEnum.SMS_FROM.toString())) {
    	  servletContext.setAttribute(ApplicationConfigurationEnum.SMS_FROM.toString(), applicationSettings.getValue());
      } else if (applicationSettings.getName().equals(ApplicationConfigurationEnum.SMS_TYPE_ID.toString())) {
    	  servletContext.setAttribute(ApplicationConfigurationEnum.SMS_TYPE_ID.toString(), applicationSettings.getValue());
      } else if (applicationSettings.getName().equals(ApplicationConfigurationEnum.SMS_DLR_ID.toString())) {
    	  servletContext.setAttribute(ApplicationConfigurationEnum.SMS_DLR_ID.toString(), applicationSettings.getValue());
      } else if (applicationSettings.getName().equals(ApplicationConfigurationEnum.ALLOW_SMS_NOTIFICATION.toString())) {
			servletContext.setAttribute(ApplicationConfigurationEnum.ALLOW_SMS_NOTIFICATION.toString(), applicationSettings.getValue());
      } 
    }
  }

}
