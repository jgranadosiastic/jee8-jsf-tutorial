package com.jgranados.journals.notifications.consumer;

import static com.jgranados.journals.config.ResourceConstants.JMS_TOPIC_DESTINATION;

import com.jgranados.journals.notifications.dto.NotificationMessageBody;
import com.jgranados.journals.notifications.service.EmailService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * JEE8_Tutorial-ejb
 *
 * @author jose - 06.11.2018
 * @Title: NotificationConsumer
 * @Description: description
 *
 * Changes History
 */
@MessageDriven(
        activationConfig = {
            @ActivationConfigProperty(
                    propertyName = "destinationLookup",
                    propertyValue = JMS_TOPIC_DESTINATION),
            @ActivationConfigProperty(
                    propertyName = "destinationType",
                    propertyValue = "javax.jms.Topic")
        }
)
public class NotificationConsumer implements MessageListener {

    @EJB
    private EmailService emailService;

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof ObjectMessage) {
                Object objectInMessage = ((ObjectMessage) message).getObject();
                if (objectInMessage instanceof NotificationMessageBody) {
                    NotificationMessageBody messageBody = (NotificationMessageBody) objectInMessage;
                    emailService.sendEmailBasedOnNotificationMessage(messageBody);
                }
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.WARNING,
                        "Message of wrong type: {0}",
                        message.getClass().getName());
            }
        } catch (JMSException exception) {
            Logger.getLogger(
                    this.getClass().getName()).log(Level.SEVERE,
                    "JMSException: {0}",
                    exception.toString());
        }
    }
}
