package com.utility;

import com.dto.FeedbackDto;

public class MailSmsContents {
	private MailSmsContents() {
    }

    public static class ForgotPassword {
		private ForgotPassword() {

		}

		public static final String MAILSUBJECT = ApplicationConstants.APP_NAME+" - Forgot Password";

		public static final String getMailBody(final String name, final String password) {
			final StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("Dear ");
			stringBuilder.append(name);
			stringBuilder.append(",<br/><br/>");
			stringBuilder.append("Your password has been reset to:<br/>");
			stringBuilder.append("Password : ");
			stringBuilder.append(password);
			stringBuilder.append("<br/><br/>");
			stringBuilder.append("Regards,<br/>");
			stringBuilder.append("Admin<br/>");
			stringBuilder.append("Application Name : " + ApplicationConstants.APP_NAME + "<br>");
			return stringBuilder.toString();
		}

		public static String getSMSBody(final String name, final String password) {
			final StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("Dear ");
			stringBuilder.append(name);
			stringBuilder.append(",\n\nYour password has been reset to:\n");
			stringBuilder.append("Password : ");
			stringBuilder.append(password);
			return stringBuilder.toString();
		}

	}

	public static class AddUser{
		private AddUser(){
			
		}
		public static final String MAILSUBJECT = ApplicationConstants.APP_NAME+" - New User";
		
		public static final String getMailBody(final String name, final String emailId, final String mobile, final String password){
			final StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("Dear ");
			stringBuilder.append(name);
			stringBuilder.append(",<br/><br/>");
			stringBuilder.append("An account has been created for you on "+ApplicationConstants.APP_NAME+".<br/>");
			stringBuilder.append("<br/>");
			stringBuilder.append("User Email : ");
			stringBuilder.append(emailId);
			stringBuilder.append("<br/>");
			stringBuilder.append("User Mobile : ");
			stringBuilder.append(mobile);
			stringBuilder.append("<br/>");
			stringBuilder.append("Password : ");
			stringBuilder.append(password);
			stringBuilder.append("<br/><br/>");
			stringBuilder.append("Regards,<br/>");
			stringBuilder.append("Admin<br/>");
			stringBuilder.append("Application Name : " + ApplicationConstants.APP_NAME + "<br>");
			return stringBuilder.toString();
		}
		
		public static final String getWelcomeSmsBody(final String name, final String emailId){
			final StringBuilder mailContent = new StringBuilder("");
			mailContent.append("Hi "+name+",\n\n");
			mailContent.append("Thank you for joining "+ApplicationConstants.APP_NAME +".\n");
			mailContent.append("Your details has been shared on "+emailId);
			return mailContent.toString();
		}
	}
	
	
	public static class ReferralUser{
		private ReferralUser(){
			
		}
		
		public static final String getSMSBody(String name, String referralCode){
			final StringBuilder mailContent = new StringBuilder("");
			mailContent.append("Hi "+",\n\n");
			mailContent.append(name + " has shared you the referral code: "+referralCode +".\n");
			mailContent.append("use this code to sign up on "+ ApplicationConstants.APP_NAME);
			return mailContent.toString();
		}
		
		
		
	}
	public static class Feedback{
		private Feedback(){
			
		}
		public static final String MAILSUBJECT = ApplicationConstants.APP_NAME+" - Feedback";
		
		public static final String USERMAILSUBJECT = "Feedback received";
		
		public static final String getMailBody(FeedbackDto feedbackDto){
			String feedback = feedbackDto.getMessage();
			String name = feedbackDto.getName();
			String email = feedbackDto.getEmail();
			String toc = feedbackDto.getToc();
			String accessType = feedbackDto.getAccessType();
			String versionNum = feedbackDto.getVersionNum();
			final StringBuilder mailContent = new StringBuilder("");
			mailContent.append("Dear Admin,<br><br>");
			mailContent.append("<span style='white-space: pre-wrap;'>"+feedback + "</span><br><br>");
			mailContent.append("Regards,<br>");
			mailContent.append(name + "<br>");
			mailContent.append(email + "<br>");
			mailContent.append(toc+ "<br>");
			 mailContent.append("Application Name : " + ApplicationConstants.APP_NAME+" "+accessType+"<br>");
		      mailContent.append("Version : " + versionNum+"<br><br>");
			return mailContent.toString();
		}
		
		public static final String getUserMailBody(final String toMailName){
			final StringBuilder mailContent = new StringBuilder("");
			mailContent.append("Hello "+toMailName+",<br><br>");
			mailContent.append("Thank you very much for sending us feedback. This is vital for us to understand your needs and improve the app.<br/><br/>");
			mailContent.append("We will contact you if more information is required.<br/><br/>");
			mailContent.append("Regards,<br>");
			mailContent.append("Admin<br>");
			return mailContent.toString();
		}
		
	}
	
	public static class MessageMail{
		private MessageMail(){
			
		}
		public static final String MAILSUBJECT = ApplicationConstants.APP_NAME+" - Message received";
		
		public static final String getMailBody(String message, String toUserName, String fromUserName, String toc){
//			String message = messageDto.getMessage();
			final StringBuilder mailContent = new StringBuilder("");
			mailContent.append("Dear " + toUserName + ",<br><br>");
			mailContent.append("<span style='white-space: pre-wrap;'>"+message + "</span><br><br>");
			mailContent.append("Regards,<br>");
			mailContent.append(fromUserName+"<br>");
			mailContent.append(toc+ "<br>");
			mailContent.append("Application Name : " + ApplicationConstants.APP_NAME+"<br>");
			return mailContent.toString();
		}
		
		
		
	}
}
