package com.jgranados.journals.subscription.service;

import static com.jgranados.journals.config.ResourceConstants.PERSISTENCE_UNIT;

import com.jgranados.journals.authentication.service.AuthenticationService;
import com.jgranados.journals.journal.domain.Journal;
import com.jgranados.journals.journal.repository.JournalRepository;
import com.jgranados.journals.notifications.service.NotificationService;
import com.jgranados.journals.subscription.domain.JournalSubscription;
import com.jgranados.journals.user.domain.User;
import com.jgranados.journals.user.repository.UserRepository;
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

    private static final String SUBSCRIPTION_MESSAGE_KEY = "NewSubscriptionMessage";
    private static final String SUBSCRIPTION_SUBJECT_KEY = "NewSubscriptionSubject";
    private static final String UNSUBSCRIPTION_MESSAGE_KEY = "TerminatedSubscriptionMessage";
    private static final String UNSUBSCRIPTION_SUBJECT_KEY = "TerminatedSubscriptionSubject";

    @PersistenceContext(unitName = PERSISTENCE_UNIT)
    private EntityManager entityManager;

    @EJB
    private AuthenticationService authenticationService;

    @EJB
    private JournalRepository journalRepository;

    @EJB
    private UserRepository userRepository;

    @EJB
    private NotificationService notificationService;

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
        notificateSubscriptionToJournal(journal);
        return journalSubscription;
    }

    /**
     * Method to remove the subscription to a journal
     *
     * @param idSubscription
     */
    public void removeSubscription(final Integer idSubscription) {
        JournalSubscription subscription = entityManager.find(JournalSubscription.class, idSubscription);
        Journal journal = subscription.getJournal();
        entityManager.remove(subscription);
        notificateUnsubscription(journal);
    }

    private void notificateSubscriptionToJournal(final Journal journal) {
        User owner = userRepository.getUserByProfile(journal.getOwnerProfile()).get();
        User subscriber = authenticationService.getAuthenticatedUser().get();
        String message = notificationService.getLocalizedText(SUBSCRIPTION_MESSAGE_KEY);
        String subject = notificationService.getLocalizedText(SUBSCRIPTION_SUBJECT_KEY);
        String journalName = journal.getName();
        String userName = subscriber.getUserName();
        notificationService.sendNotification(owner,
                String.format(subject, journalName),
                String.format(message, userName, journalName));
    }

    private void notificateUnsubscription(final Journal journal) {
        User owner = userRepository.getUserByProfile(journal.getOwnerProfile()).get();
        User subscriber = authenticationService.getAuthenticatedUser().get();
        String message = notificationService.getLocalizedText(UNSUBSCRIPTION_MESSAGE_KEY);
        String subject = notificationService.getLocalizedText(UNSUBSCRIPTION_SUBJECT_KEY);
        String journalName = journal.getName();
        String userName = subscriber.getUserName();
        notificationService.sendNotification(owner,
                String.format(subject, journalName),
                String.format(message, journalName, userName));
    }
}
