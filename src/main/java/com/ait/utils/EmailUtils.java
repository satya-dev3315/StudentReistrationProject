package com.ait.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtils {

	@Autowired
	private JavaMailSender javaMailSender;

	// send mail to recover pwd

	public boolean sendEmail(String subject, String body, String to) { // to whom i need to send the mail

		boolean isSent = false;

		try {

			MimeMessage createMimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(createMimeMessage);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, true); // true means we wanna send html too along with body

			javaMailSender.send(createMimeMessage);

			isSent = true; // if try block executed withour any problem , method will b true,otherwise it
							// will return down isSent(which we set false on above)

		} catch (Exception e) {
			e.printStackTrace();
		}
		return isSent;

	}

}
