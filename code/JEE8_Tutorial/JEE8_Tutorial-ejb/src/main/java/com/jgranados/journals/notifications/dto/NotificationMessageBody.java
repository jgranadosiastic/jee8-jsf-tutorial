package com.jgranados.journals.notifications.dto;

import com.jgranados.journals.user.domain.User;
import java.io.Serializable;
import java.util.List;

/**
 * JEE8_Tutorial-ejb
 *
 * @author jose - 08.11.2018
 * @Title: NotificationMessageBody
 * @Description: description
 *
 * Changes History
 */
public class NotificationMessageBody implements Serializable {

    private List<User> users;
    private String subject;
    private String emailText;

    public NotificationMessageBody(List<User> users,
            String subject,
            String emailText) {
        this.users = users;
        this.subject = subject;
        this.emailText = emailText;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmailText() {
        return emailText;
    }

    public void setEmailText(String emailText) {
        this.emailText = emailText;
    }

}
