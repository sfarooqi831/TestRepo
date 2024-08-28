package com.alivia.bussiness.dto;

public class EmailRequest {

	private String reciver;
	private String subject;
	private String emailBody;
	private String [] attachement;
	
	

	public String[] getAttachement() {
		return attachement;
	}

	public void setAttachement(String [] attachement) {
		this.attachement = attachement;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EmailRequest [reciver=");
		builder.append(reciver);
		builder.append(", subject=");
		builder.append(subject);
		builder.append(", emailBody=");
		builder.append(emailBody);
		builder.append(", attachement=");
		builder.append(attachement);
		builder.append("]");
		return builder.toString();
	}

	public EmailRequest(String reciver, String subject, String emailBody) {
		super();
		this.reciver = reciver;
		this.subject = subject;
		this.emailBody = emailBody;
	}

	public String getReciver() {
		return reciver;
	}

	public void setReciver(String reciver) {
		this.reciver = reciver;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getEmailBody() {
		return emailBody;
	}

	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
	}
}
