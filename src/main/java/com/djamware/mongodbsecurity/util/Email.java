package com.djamware.mongodbsecurity.util;

import com.djamware.mongodbsecurity.constants.Constants;
import com.djamware.mongodbsecurity.exception.IncorrectEmailSendException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Email {
    public static void sendEmail(final String userName,
                                 final String password,
                                 List<String> toAddress,
                                 String subject,
                                 String message) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", Constants.HOST);
        properties.put("mail.smtp.port", Constants.PORT);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.user", userName);
        properties.put("mail.password", password);

        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
        try {
            Session session = Session.getInstance(properties, auth);
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(userName));
            InternetAddress[] toAddresses = new InternetAddress[toAddress.size()];
            for (int i = 0; i < toAddress.size(); i++) {
                toAddresses[i] = new InternetAddress(toAddress.get(i));
            }
            msg.setRecipients(Message.RecipientType.TO, toAddresses);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(message, "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            msg.setContent(multipart);
            Transport.send(msg);
        } catch (MessagingException e) {
            throw new IncorrectEmailSendException("Incorrect email address");
        }
    }
}
