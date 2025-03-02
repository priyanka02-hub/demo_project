package com.project.service;

import com.project.model.UserMaster;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    UserMaster userMaster;

    public void sendUserDetailsMail(String to, String subject, String message) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(message, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendUserDetails(UserMaster user, String action) {
        String subject = "User " + action + " - " + user.getName();
        String message = "<h3>User " + action + "</h3>"
                + "<p><strong>Name:</strong> " + user.getName() + "</p>"
                + "<p><strong>Email:</strong> " + user.getEmail() + "</p>"
                + "<p><strong>Salary:</strong> " + user.getSalary() + "</p>"
                + "<p><strong>Designation:</strong> " + user.getDesignation() + "</p>"
                + "<p><strong>Role:</strong> " + user.getRole().getRoleName() + "</p>";

        sendUserDetailsMail(user.getEmail(), subject, message);
    }

    public void sendDeactivationMail(UserMaster user) {
        String subject = "Account Deactivated - " + user.getName();
        String message = "<h3>Your account has been deactivated.</h3>"
                + "<p><strong>Name:</strong> " + user.getName() + "</p>"
                + "<p><strong>Email:</strong> " + user.getEmail() + "</p>"
                + "<p>If you have any concerns, please contact support.</p>";

        sendUserDetailsMail(user.getEmail(), subject, message);
    }
}
