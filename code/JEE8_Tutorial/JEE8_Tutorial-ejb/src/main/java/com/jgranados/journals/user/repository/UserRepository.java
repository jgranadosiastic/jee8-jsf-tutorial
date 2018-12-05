package com.jgranados.journals.user.repository;

import static com.jgranados.journals.config.ResourceConstants.PERSISTENCE_UNIT;

import com.jgranados.journals.journal.domain.Journal;
import com.jgranados.journals.user.domain.Profile;
import com.jgranados.journals.user.domain.User;
import java.util.List;
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
    private static final String BY_SUBSCRIBED_TO_JOURNAL = "SELECT s.user FROM JournalSubscription s Where s.journal = :journal";
    private static final String BY_PROFILE = "SELECT u FROM User u Where u.profile = :profile";

    @PersistenceContext(unitName = PERSISTENCE_UNIT)
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

    /**
     * Method to get the users subscribed to a journal
     *
     * @param journal
     * @return The list of users
     */
    public List<User> getUsersBySubscribedToJournal(final Journal journal) {
        Query query = entityManager.createQuery(BY_SUBSCRIBED_TO_JOURNAL);
        query.setParameter("journal", journal);
        return query.getResultList();
    }

    /**
     * Method to get the user by its profile
     *
     * @param journal
     * @return Optional with the user or empty
     */
    public Optional<User> getUserByProfile(final Profile profile) {
        Query query = entityManager.createQuery(BY_PROFILE);
        query.setParameter("profile", profile);
        return query.getResultStream().findFirst();
    }
}
