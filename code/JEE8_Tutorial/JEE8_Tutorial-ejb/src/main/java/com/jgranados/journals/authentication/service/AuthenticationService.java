package com.jgranados.journals.authentication.service;

import com.jgranados.journals.user.domain.User;
import com.jgranados.journals.user.repository.UserRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

/**
 *
 * @author jose
 */
@Stateless
@LocalBean
@Named
public class AuthenticationService {

    @Resource
    SessionContext securityContext;

    @EJB
    UserRepository userRepository;

    @Inject
    private Pbkdf2PasswordHash pbkdf2PasswordHash;

    public void login() {
        Map<String, String> map = new HashMap<>();
        map.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA256");
        map.put("Pbkdf2PasswordHash.SaltSizeBytes", "64");
        map.put("Pbkdf2PasswordHash.KeySizeBytes", "64");
        pbkdf2PasswordHash.initialize(map);
        System.out.println("PASSWOWRD:" + pbkdf2PasswordHash.generate(new char[]{'1', '2', '3', '4', '5'}));

    }

    /**
     * Method to get the authenticated user using the securitycontext and the user principal
     *
     * @return
     */
    public Optional<User> getAuthenticatedUser() {
        String userName = securityContext.getCallerPrincipal().getName();
        return userRepository.getUserByUserName(userName);
    }
}
