package com.jgranados.journals.logout.view;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * JEE8_Tutorial-web
 *
 * @author jose - 18.10.2018
 * @Title: LogoutView
 * @Description: description
 *
 * Changes History
 */
@Named
@RequestScoped
public class LogoutView {

    @Inject
    private HttpServletRequest request;

    public String logout()
            throws ServletException {
        request.logout();
        request.getSession().invalidate();
        return "logout";
    }
}
