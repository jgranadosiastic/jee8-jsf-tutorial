package com.jgranados.journals.utils;

import java.util.Locale;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.application.FacesMessage.SEVERITY_WARN;
import javax.faces.context.FacesContext;

/**
 * JEE8_Tutorial-web
 *
 * @author jose - 19.10.2018
 * @Title: MesssageUtils
 * @Description: description
 *
 * Changes History
 */
public class MessageUtils {

    public static final String PATH_MESSAGES_BUNDLE = "com.jgranados.journals.i18n.messages";
    public static final String PATH_LABELS_BUNDLE = "com.jgranados.journals.i18n.labels";

    public static void addErrorMessage(final String msg) {
        addMessage(msg, SEVERITY_ERROR);
    }

    public static void addSuccessMessage(final String msg) {
        addMessage(msg, SEVERITY_INFO);
    }

    public static void addWarningMessage(final String key) {
        addMessage(key, SEVERITY_WARN);
    }

    public static void addErrorLocalizedMessage(final String key) {
        addLocalizedMessage(key, SEVERITY_ERROR);
    }

    public static void addSuccessLocalizedMessage(final String key) {
        addLocalizedMessage(key, SEVERITY_INFO);
    }

    public static void addWarningLocalizedMessage(final String key) {
        addLocalizedMessage(key, SEVERITY_WARN);
    }

    public static String getLocalizedMessage(final String key) {
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        return ResourceBundle.getBundle(PATH_MESSAGES_BUNDLE, locale).getString(key);
    }

    public static String getLocalizedLabel(final String key) {
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        return ResourceBundle.getBundle(PATH_LABELS_BUNDLE, locale).getString(key);
    }

    private static void addMessage(final String msg,
                                   final FacesMessage.Severity severity) {
        FacesMessage facesMsg = new FacesMessage(severity, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    private static void addLocalizedMessage(final String key,
                                            final FacesMessage.Severity severity) {
        final String message = getLocalizedMessage(key);
        addMessage(message, severity);
    }
}
