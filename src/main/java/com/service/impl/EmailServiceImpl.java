package com.service.impl;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.dto.EmailDto;
import com.service.IEmailService;
import com.utility.ApplicationConfigurationEnum;

@Component
public class EmailServiceImpl implements IEmailService {
    private static final Logger LOGGER = LogManager.getLogger(EmailServiceImpl.class);

    @Autowired
    private ServletContext appConfigDto;

    @Override
    public void sendMail(EmailDto emailDto) throws MessagingException {
    	Boolean allowMailNotification = Boolean.parseBoolean(appConfigDto.getAttribute(ApplicationConfigurationEnum.ALLOW_MAIL_NOTIFICATION.toString()).toString());
    	if(allowMailNotification){
			LOGGER.info("sendmail " + emailDto.toString());
			JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
			mailSender.setHost(appConfigDto.getAttribute(ApplicationConfigurationEnum.SMTP_HOST.toString()).toString());
			mailSender.setPort(Integer.parseInt(appConfigDto.getAttribute(ApplicationConfigurationEnum.SMTP_PORT.toString()).toString()));
			mailSender.setUsername(appConfigDto.getAttribute(ApplicationConfigurationEnum.SMTP_USERNAME.toString()).toString());
			mailSender.setPassword(appConfigDto.getAttribute(ApplicationConfigurationEnum.SMTP_PASSWORD.toString()).toString());
			final Properties javaMailProperties = new Properties();
		
			javaMailProperties.put("mail.smtp.auth", appConfigDto.getAttribute(ApplicationConfigurationEnum.SMTP_AUTHENTICATION.toString()).toString());
			javaMailProperties.put("mail.smtp.starttls.enable", appConfigDto.getAttribute(ApplicationConfigurationEnum.SMTP_TLS_ENABLED.toString()).toString());
			mailSender.setJavaMailProperties(javaMailProperties);
			final MimeMessage mimeMessage = mailSender.createMimeMessage();
			final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom(appConfigDto.getAttribute(ApplicationConfigurationEnum.SMTP_EMAIL_EXEC.toString()).toString());
			helper.setTo(emailDto.getTo());
			if (emailDto.getCc() != null) {
			    helper.setCc(emailDto.getCc());
			}
			if (emailDto.getFile() != null && emailDto.getFileName() != null) {
			    helper.addAttachment(emailDto.getFileName(), emailDto.getFile());
			}
			helper.setSubject(emailDto.getSubject());
			helper.setText(emailDto.getMailBody(), emailDto.isHtml());
			mailSender.send(mimeMessage);
			LOGGER.info("sendmail completed for " + emailDto.toString());
    	}
    }

}
