package com.leavemanagement.serviceimpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import javax.mail.Session;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leavemanagement.dto.EmployeeLeaveDTO;
import com.leavemanagement.model.User;
import com.leavemanagement.repository.UserRepository;
import com.leavemanagement.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {
	@Autowired
	UserRepository userRepository;

	public List<User> getAdmin() {
		List<User> userList = userRepository.findAll();
		List<User> admin = new ArrayList<>();
		for (User user : userList) {
			if (user.getRole().equalsIgnoreCase("ADMIN_ROLE")) {
				admin.add(user);
			}
		}
		return admin;
	}

	@Override
	public void sendMail(EmployeeLeaveDTO employeeLeaveDTO) {
		String from = "xyz@gmail.com";
		List<User> adminList = getAdmin();
		String subject = "Leave Application for a "+employeeLeaveDTO.getType()+" Day";
		String host = "smtp.gmail.com";
		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", 465);
		properties.put("mail.smtp.ssl.enable", true);
		properties.put("mail.smtp.auth", true);
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, "password");
			}
		});
		try {
			for (User admin : adminList) {
				MimeMessage msg = new MimeMessage(session);
				msg.setFrom(from);
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(admin.getEmail()));
				msg.setSubject(subject);
				String html;
			    SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
			    String date = outputFormat.format(employeeLeaveDTO.getFromDate());
			    String toDate = outputFormat.format(employeeLeaveDTO.getToDate());

				if (employeeLeaveDTO.getType().equalsIgnoreCase("half")) {
					html = "<h4> Dear Mr./Mrs "+admin.getName()+", <br> I would like to inform you that I require a half day of absence on "
							+ date + ".</h4>";
				} else {
					if(Objects.equals(date, toDate)) {
						html = "<h4> Dear Mr./Mrs "+admin.getName()+", <br> I would like to inform you that I require a day of absence on "
								+ date  + ".</h4>";
					}else {
						html = "<h4> Dear Mr./Mrs "+admin.getName()+", <br> I would like to inform you that I require a days of absence on "
								+ date + " to " + toDate + ".</h4>";
					}
				}
				msg.setText(html, "UTF-8", "html");
				Transport.send(msg);
				System.out.println("sent success........");
			}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}

	}

}
