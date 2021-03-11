package com.ca.serviceinterface;

import javax.mail.MessagingException;

public interface EmailService {

	void sendSimpleMessage(String recipientAddress, String subject, String text);

	void sendMessageWithAttachment(
			  String to, String subject, String text, String pathToAttachment) throws MessagingException;
}
