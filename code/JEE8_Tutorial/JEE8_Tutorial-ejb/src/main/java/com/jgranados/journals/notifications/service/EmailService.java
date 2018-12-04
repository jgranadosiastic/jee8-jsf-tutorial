package com.jgranados.journals.notifications.service;

import static com.jgranados.journals.config.ResourceConstants.JDBC_RESOURCE;

import com.jgranados.journals.notifications.dto.NotificationMessageBody;
import com.jgranados.journals.user.domain.User;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * JEE8_Tutorial-ejb
 *
 * @author jose - 08.11.2018
 * @Title: EmailService
 * @Description: description
 *
 * Changes History
 */
@Stateless
@LocalBean
public class EmailService {

    public static final String CONTENT_TYPE = "text/html; charset=utf-8";
    public static final String EMAIL_ADDESS_SEPARATOR = ",";
    @Resource(name = JDBC_RESOURCE)
    private Session emailSession;

    public void sendEmailBasedOnNotificationMessage(
            final NotificationMessageBody notificationBody) {
        final String addressList = notificationBody
                .getUsers()
                .stream()
                .map(User::getEmail)
                .collect(Collectors.joining(EMAIL_ADDESS_SEPARATOR));
        sendEmail(addressList, notificationBody.getSubject(), notificationBody.getEmailText());
    }

    private void sendEmail(final String to,
            final String subject,
            final String body) {
        try {
            Message message = new MimeMessage(emailSession);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setContent(body, CONTENT_TYPE);
            Transport.send(message);
        } catch (MessagingException ex) {
            Logger.getLogger(EmailService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
