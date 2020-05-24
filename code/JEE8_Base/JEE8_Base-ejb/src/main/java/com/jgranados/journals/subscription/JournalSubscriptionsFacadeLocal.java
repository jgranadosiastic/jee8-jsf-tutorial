package com.jgranados.journals.subscription;

import com.jgranados.journals.subscription.domain.JournalSubscription;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.ejb.Local;

/**
 * JEE8_Tutorial-ejb
 *
 * @author jose - 25.10.2018
 * @Title: JournalSubscriptionsFacadeLocal
 * @Description: description
 *
 * Changes History
 */
@Local
public interface JournalSubscriptionsFacadeLocal {

    public JournalSubscription subscribeToJournal(final Integer idJournal);

    public void removeSubscription(final Integer idSubscription);

    public List<JournalSubscription> searchMySubscriptions(final String journalName,
                                                           final Date subscriptionDateIni,
                                                           final Date subscriptionDateEnd);

    public Optional<JournalSubscription> getJournalSubscriptionById(final Integer idJournalSubscription);

}
