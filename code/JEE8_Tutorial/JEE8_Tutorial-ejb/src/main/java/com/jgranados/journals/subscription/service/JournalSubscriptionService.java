package com.jgranados.journals.subscription.service;

import static com.jgranados.journals.config.ResourceConstants.PERSISTENCE_UNIT;

import com.jgranados.journals.authentication.service.AuthenticationService;
import com.jgranados.journals.journal.domain.Journal;
import com.jgranados.journals.journal.repository.JournalRepository;
import com.jgranados.journals.subscription.domain.JournalSubscription;
import com.jgranados.journals.user.domain.User;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * JEE8_Tutorial-ejb
 *
 * @author jose - 25.10.2018
 * @Title: JournalSubscriptionService
 * @Description: description
 *
 * Changes History
 */
@Stateless
@LocalBean
public class JournalSubscriptionService {

    @PersistenceContext(unitName = PERSISTENCE_UNIT)
    private EntityManager entityManager;

    @EJB
    AuthenticationService authenticationService;

    @EJB
    JournalRepository journalRepository;

    /**
     * Method to subscribe to a journal
     *
     * @param idJournal
     * @return the subscription created
     */
    public JournalSubscription subscribeToJournal(final Integer idJournal) {
        User creator = authenticationService.getAuthenticatedUser().get();
        Journal journal = journalRepository.getJournalById(idJournal).get();
        JournalSubscription journalSubscription = new JournalSubscription();
        journalSubscription.setJournal(journal);
        journalSubscription.setUser(creator);
        journalSubscription.setSubscriptionDate(new Date());
        entityManager.persist(journalSubscription);
        return journalSubscription;
    }

    /**
     * Method to remove the subscription to a journal
     *
     * @param idSubscription
     */
    public void removeSubscription(final Integer idSubscription) {
        entityManager.remove(entityManager.find(JournalSubscription.class, idSubscription));
    }
}
