package com.cronjob;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.utility.ServletContextInjector;

@Component
public class CronJobImpl implements ICronJob {
  private static final Logger LOGGER = LogManager.getLogger(CronJobImpl.class);
  @Autowired
  private ServletContextInjector servletContextInjector;

  
  @Override
  @Scheduled(cron = "0 0 0 * * ?")
  public void updateApplicationConfiguration() {
    try {
      servletContextInjector.upateContext();
    } catch (Exception e) {
      LOGGER.error("Error in updateApplicationConfiguration job : ", e);
    }
  }
  

}
