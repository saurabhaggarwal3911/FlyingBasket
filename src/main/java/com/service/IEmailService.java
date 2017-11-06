package com.service;

import javax.mail.MessagingException;

import com.dto.EmailDto;

public interface IEmailService {
  void sendMail(EmailDto emailDto) throws MessagingException;
}
