package com.jgranados.journals.config;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;

/**
 * JEE8_Tutorial-web
 *
 * @author jose - 18.10.2018
 * @Title: ApplicationConfig
 * @Description: description
 *
 * Changes History
 */
@CustomFormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage = "/login.xhtml",
                errorPage = "",
                useForwardToLogin = false
        )
)
@ApplicationScoped
@FacesConfig
public class SecurityConfig {

    /**
     * Creates a new instance of ApplicationConfig
     */
    public SecurityConfig() {
    }

}
