package com.jgranados.journals.notifications.service;

import com.jgranados.journals.notifications.dto.NotificationMessageBody;
import com.jgranados.journals.user.domain.User;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.ObjectMessage;
import javax.jms.Topic;

/**
 * JEE8_Tutorial-ejb
 *
 * @author jose - 06.11.2018
 * @Title: NotificationService
 * @Description: description
 *
 * Changes History
 */
@Stateless
@LocalBean
public class NotificationService {

    public static final String NOTIFICATIONS_BUNDLE = "com.jgranados.journals.notifications.i18n.NotificationsBundle";
    @Resource(mappedName = "jms/__JEE8TutorialTopic")
    private Topic jee8TutorialTopic;

    @Inject
    private JMSContext jmsContext;

    @Inject
    private FacesContext facesContext;
    
    public void sendNotification(List<User> users,
            String subject,
            String notificationBody) {
        ObjectMessage message = jmsContext.createObjectMessage(
                new NotificationMessageBody(users, subject, notificationBody));
        jmsContext.createProducer().send(jee8TutorialTopic, message);
    }

    public String getLocalizedText(String key) {
        ResourceBundle messages = ResourceBundle.getBundle(NOTIFICATIONS_BUNDLE,
                facesContext.getViewRoot().getLocale());
        return messages.getString(key);
    }
}
