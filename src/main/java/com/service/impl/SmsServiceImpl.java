package com.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.servlet.ServletContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.service.ISmsService;
import com.utility.ApplicationConfigurationEnum;
import com.utility.ApplicationConstants;

/**
 * This class is used to call integrate with O2 SMS service.
 * 
 * @author saggarwal
 * 
 */
@Component
public class SmsServiceImpl implements ISmsService {

	private static final Logger LOG = LogManager.getLogger(SmsServiceImpl.class);

	@Autowired
	private ServletContext appConfigDto;

	/**
	 * call SMS service
	 */
	@Override
	public void sendSMS(String phoneNo, final String messageBody) throws IOException {
		Boolean allowSmsNotification = Boolean.parseBoolean(appConfigDto.getAttribute(ApplicationConfigurationEnum.ALLOW_SMS_NOTIFICATION.toString()).toString());
		if (allowSmsNotification) {
			OutputStreamWriter wr = null;
			BufferedReader rd = null;
			try {
				String data = "";
				phoneNo = phoneNo.startsWith("+") ? phoneNo.substring(1) : phoneNo;
				final StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append("username=");
				stringBuilder.append(URLEncoder.encode(appConfigDto.getAttribute(ApplicationConfigurationEnum.SMS_USERNAME.toString()).toString(), ApplicationConstants.ISO_8859_1));
				stringBuilder.append("&password=");
				stringBuilder.append(URLEncoder.encode(appConfigDto.getAttribute(ApplicationConfigurationEnum.SMS_PASSWORD.toString()).toString(), ApplicationConstants.ISO_8859_1));
				stringBuilder.append("&type=");
				stringBuilder.append(URLEncoder.encode(appConfigDto.getAttribute(ApplicationConfigurationEnum.SMS_TYPE_ID.toString()).toString(), ApplicationConstants.ISO_8859_1));
				stringBuilder.append("&dlr=");
				stringBuilder.append(URLEncoder.encode(appConfigDto.getAttribute(ApplicationConfigurationEnum.SMS_DLR_ID.toString()).toString(), ApplicationConstants.ISO_8859_1));
				stringBuilder.append("&destination=91");
				stringBuilder.append(URLEncoder.encode(phoneNo, ApplicationConstants.ISO_8859_1));
				stringBuilder.append("&source=");
				stringBuilder.append(URLEncoder.encode(appConfigDto.getAttribute(ApplicationConfigurationEnum.SMS_FROM.toString()).toString(), ApplicationConstants.ISO_8859_1));
				stringBuilder.append("&message=");
				stringBuilder.append(URLEncoder.encode(messageBody, ApplicationConstants.ISO_8859_1));
				// stringBuilder.append("&applicationid=");
				// stringBuilder.append(URLEncoder.encode(appConfigConstants.getSmsAppId(),
				// ApplicationConstants.ISO_8859_1));
				// stringBuilder.append("&costcenterid=");
				// stringBuilder.append(URLEncoder.encode(appConfigConstants.getSmsCostCentreId(),
				// ApplicationConstants.ISO_8859_1));

				data = stringBuilder.toString();
				final URL url = new URL(appConfigDto.getAttribute(ApplicationConfigurationEnum.SMS_URL.toString()).toString());
				URLConnection conn = url.openConnection();
				conn.setDoOutput(true);
				wr = new OutputStreamWriter(conn.getOutputStream());
				wr.write(data);
				wr.flush();
				rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String line;
				String sendSmsResponse = "";
				while ((line = rd.readLine()) != null) {
					// Print the response output...
					sendSmsResponse += line + "\n";
				}
				LOG.info("SMS sent via Airtel with phoneNo: " + phoneNo + " :messageBody: " + messageBody + " with response: " + sendSmsResponse);
			} catch (IOException e) {
				throw e;
			} finally {
				if (wr != null) {
					wr.close();
				}
				if (rd != null) {
					rd.close();
				}
			}
		}
	}

	
}
