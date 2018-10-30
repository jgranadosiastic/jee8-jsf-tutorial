package com.jgranados.journals.authentication;

import com.jgranados.journals.user.domain.User;
import java.util.Optional;
import javax.ejb.Local;

/**
 * JEE8_Tutorial-ejb
 *
 * @author jose - 21.10.2018
 * @Title: AuthenticationFacadeLocal
 * @Description: description
 *
 * Changes History
 */
@Local
public interface AuthenticationFacadeLocal {

    Optional<User> getAuthenticatedUser();
}
