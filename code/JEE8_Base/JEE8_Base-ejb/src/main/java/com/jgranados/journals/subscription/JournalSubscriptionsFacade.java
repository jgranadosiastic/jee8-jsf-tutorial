package com.jgranados.journals.subscription;

import com.jgranados.journals.subscription.domain.JournalSubscription;
//import com.jgranados.journals.subscription.repository.JournalSubscriptionRepository;
//import com.jgranados.journals.subscription.service.JournalSubscriptionService;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * JEE8_Tutorial-ejb
 *
 * @author jose - 25.10.2018
 * @Title: JournalSubscriptionsFacade
 * @Description: description
 *
 * Changes History
 */
@Stateless
public class JournalSubscriptionsFacade implements JournalSubscriptionsFacadeLocal {

    /*@EJB
    private JournalSubscriptionRepository journalSubscriptionRepository;

    @EJB
    private JournalSubscriptionService journalSubscriptionService;*/

    @Override
    public JournalSubscription subscribeToJournal(final Integer idJournal) {
        //return journalSubscriptionService.subscribeToJournal(idJournal);
        return null;
    }

    @Override
    public void removeSubscription(final Integer idSubscription) {
        //journalSubscriptionService.removeSubscription(idSubscription);
    }

    @Override
    public List<JournalSubscription> searchMySubscriptions(final String journalName,
                                                           final Date subscriptionDateIni,
                                                           final Date subscriptionDateEnd) {
        //return journalSubscriptionRepository.searchMySubscriptions(journalName, subscriptionDateIni, subscriptionDateEnd);
        return null;
    }

    @Override
    public Optional<JournalSubscription> getJournalSubscriptionById(final Integer idJournalSubscription) {
        //return journalSubscriptionRepository.getJournalSubscriptionById(idJournalSubscription);
        return null;
    }
}
