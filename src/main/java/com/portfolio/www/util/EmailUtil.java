package com.portfolio.www.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.portfolio.www.dto.EmailDto;

public class EmailUtil {
	private JavaMailSender javaMailSender;
	
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	public String sendEmail(EmailDto emailDto) {
		return sendEmail(emailDto, false);
	}
	
	public String sendEmail(EmailDto emailDto, boolean isHtml) {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			messageHelper.setTo(emailDto.getReceiver());
			messageHelper.setText(emailDto.getText());
			messageHelper.setFrom(emailDto.getFrom());
			messageHelper.setSubject(emailDto.getSubject()); // 제목은 생략 가능
			messageHelper.setText(emailDto.getText(), isHtml); // isHtml이 true인 경우 링크 클릭 가능
			javaMailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
			return "Error";
		}
		
		return "Success";
	}
}
