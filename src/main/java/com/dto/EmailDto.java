package com.dto;

import java.io.File;
import java.util.Arrays;

public class EmailDto {
    private String[] to;
    private String[] cc;
    private String[] bcc;
    private String subject;
    private String mailBody;
    private File file;
    private String fileName;
    private boolean html = true;

    public String[] getTo() {
	return to;
    }

    public void setTo(String[] to) {
	this.to = to;
    }

    public String[] getCc() {
	return cc;
    }

    public void setCc(String[] cc) {
	this.cc = cc;
    }

    public String[] getBcc() {
	return bcc;
    }

    public void setBcc(String[] bcc) {
	this.bcc = bcc;
    }

    public String getSubject() {
	return subject;
    }

    public void setSubject(String subject) {
	this.subject = subject;
    }

    public String getMailBody() {
	return mailBody;
    }

    public void setMailBody(String mailBody) {
	this.mailBody = mailBody;
    }

    public File getFile() {
	return file;
    }

    public void setFile(File file) {
	this.file = file;
    }

    public String getFileName() {
	return fileName;
    }

    public void setFileName(String fileName) {
	this.fileName = fileName;
    }

    public boolean isHtml() {
	return html;
    }

    public void setHtml(boolean html) {
	this.html = html;
    }

    @Override
    public String toString() {
	// TODO Auto-generated method stub
	return "to : " + Arrays.toString(to) + ", cc : " + Arrays.toString(cc) + ", bcc : " + Arrays.toString(bcc) + ", subject : " + subject + ", fileName : " + fileName;
    }

    
}
