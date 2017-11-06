package com.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {
	private Pattern pattern;
	private final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public EmailValidator() {
		pattern = Pattern.compile(EMAIL_REGEX);
	}

	public boolean validate(String value) {
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}
}
