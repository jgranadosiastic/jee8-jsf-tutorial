package com.jgranados.journals.login.view;

import com.jgranados.journals.authentication.AuthenticationFacadeLocal;
import static com.jgranados.journals.authentication.enums.RoleEnum.PUBLIC;
import static com.jgranados.journals.authentication.enums.RoleEnum.PUBLISHER;
import com.jgranados.journals.user.domain.User;
import com.jgranados.journals.utils.MessageUtils;
import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * JEE8_Tutorial-web
 *
 * @author jose - 18.10.2018
 * @Title: LoginView
 * @Description: description
 *
 * Changes History
 */
@Named
@ViewScoped
public class LoginView implements Serializable {

    @EJB
    private AuthenticationFacadeLocal authenticationFacade;

    @Inject
    private SecurityContext securityContext;

    @Inject
    private ExternalContext externalContext;

    @Inject
    private FacesContext facesContext;

    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void login()
            throws IOException {
        Credential credential = new UsernamePasswordCredential(userName, new Password(password));
        AuthenticationStatus status = securityContext.authenticate(
                getRequest(),
                getResponse(),
                AuthenticationParameters.withParams().credential(credential));
        switch (status) {
            case SEND_CONTINUE:
                facesContext.responseComplete();
                break;
            case SEND_FAILURE:
                MessageUtils.addErrorLocalizedMessage("loginfailed");
                break;
            case SUCCESS:
                redirectToIndex();
            case NOT_DONE:
        }
    }

    private HttpServletRequest getRequest() {
        return (HttpServletRequest) externalContext.getRequest();
    }

    private HttpServletResponse getResponse() {
        return (HttpServletResponse) externalContext.getResponse();
    }

    private void redirectToIndex()
            throws IOException {
        User currentUser = authenticationFacade.getAuthenticatedUser().get();
        switch (currentUser.getUserRole()) {
            case PUBLISHER:
                externalContext.redirect(externalContext.getRequestContextPath() + "/journals/journals.xhtml");
                break;
            case PUBLIC:
                externalContext.redirect(externalContext.getRequestContextPath() + "/subscriptions/subscriptions.xhtml");
                break;
        }
    }
}
