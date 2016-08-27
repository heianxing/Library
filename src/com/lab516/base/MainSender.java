package com.lab516.base;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class MainSender {

	private static final String HOST = "smtp.lzu.edu.cn";

	private static final String FROM = "xxxx@lzu.edu.cn";

	private static final String PWD = "xxxx";

	public static void send(String to, String subject, String text) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(FROM);
		mailMessage.setTo(to);
		mailMessage.setSubject(subject);
		mailMessage.setText(text);

		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		senderImpl.setHost(HOST);
		senderImpl.setUsername(FROM);
		senderImpl.setPassword(PWD);

		senderImpl.send(mailMessage);
	}

}
