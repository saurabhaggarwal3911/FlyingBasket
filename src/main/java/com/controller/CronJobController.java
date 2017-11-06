package com.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cronjob.ICronJob;

@RestController
@RequestMapping(value = "/public/cronjob")
public class CronJobController {

  @Autowired
  private ICronJob cronJob;

  @RequestMapping(value = "/run/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
  public Map<String, Object> run(@PathVariable int id) {
    Map<String, Object> response = new HashMap<String, Object>();
    switch (id) {
      case 1:
        cronJob.updateApplicationConfiguration();
        break;
      default:
        break;
    }
    response.put("success", Boolean.TRUE);

    return response;
  }
}
