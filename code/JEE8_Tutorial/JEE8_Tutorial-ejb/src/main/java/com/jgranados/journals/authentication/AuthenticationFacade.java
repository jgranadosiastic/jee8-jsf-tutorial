package com.jgranados.journals.authentication;

import com.jgranados.journals.authentication.service.AuthenticationService;
import com.jgranados.journals.user.domain.User;
import java.util.Optional;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * JEE8_Tutorial-ejb
 *
 * @author jose - 21.10.2018
 * @Title: AuthenticationFacade
 * @Description: description
 *
 * Changes History
 */
@Stateless
public class AuthenticationFacade implements AuthenticationFacadeLocal {

    @EJB
    private AuthenticationService authenticationService;

    @Override
    public Optional<User> getAuthenticatedUser() {
        return authenticationService.getAuthenticatedUser();
    }

}
