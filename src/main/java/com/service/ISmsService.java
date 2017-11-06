package com.service;

import java.io.IOException;

public interface ISmsService {

	void sendSMS(String phoneNo, String messageBody) throws IOException;

}
