package com.example.androidapp;

import android.os.Handler;
import android.os.Looper;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {
    private static final Logger logger = Logger.getLogger(MailSender.class.getName());
    private final String username = "ibrahimaminianediouf@esp.sn";
    private final String password = "xxleihvlbcudajtn";

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public void sendEmail(String toEmail, String subject, String body) {
        executor.execute(() -> {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            logger.info("Creating email session with properties: " + props);

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    logger.info("Authenticating with username: " + username);
                    return new PasswordAuthentication(username, password);
                }
            });

            try {
                logger.info("Preparing to send email to: " + toEmail);
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
                message.setSubject(subject);
                message.setText(body);

                logger.info("Sending email...");
                Transport.send(message);
                logger.info("Email sent successfully.");
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Failed to send email: " + e.getMessage(), e);
            }
        });
    }
}
