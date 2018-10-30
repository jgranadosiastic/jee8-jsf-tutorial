package com.jgranados.journals.user.repository;

import com.jgranados.journals.user.domain.User;
import java.util.Optional;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * JEE8_Tutorial-ejb
 *
 * @author jose - 17.10.2018
 * @Title: UserRepository
 * @Description: description
 *
 * Changes History
 */
@Stateless
@LocalBean
@Named
public class UserRepository {

    private static final String BY_USERNAME_AND_PASSWORD = "SELECT u FROM User u Where u.userName = :userName and u.userPassword = :password";
    private static final String BY_USERNAME = "SELECT u FROM User u Where u.userName = :userName";

    @PersistenceContext(unitName = "JEE8_Tutorial-PU")
    private EntityManager entityManager;

    /**
     * Method to get a user by name and password
     *
     * @param userName
     * @param password
     * @return Optional with the user or empty
     */
    public Optional<User> getUserByUserNameAndPassword(final String userName,
                                                       final String password) {
        Query query = entityManager.createQuery(BY_USERNAME_AND_PASSWORD);
        query.setParameter("userName", userName);
        query.setParameter("password", password);
        return query.getResultStream().findFirst();
    }

    /**
     * Method to get the user identified by its userNane
     *
     * @param userName
     * @return Optional with the user or empty
     */
    public Optional<User> getUserByUserName(final String userName) {
        Query query = entityManager.createQuery(BY_USERNAME);
        query.setParameter("userName", userName);
        return query.getResultStream().findFirst();
    }
}
