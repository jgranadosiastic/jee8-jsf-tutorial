package com.jgranados.journals.journalsubscriptions.view;

import com.jgranados.journals.journals.view.JournalsView;
import com.jgranados.journals.subscription.JournalSubscriptionsFacadeLocal;
import com.jgranados.journals.subscription.domain.JournalSubscription;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * JEE8_Tutorial-web
 *
 * @author jose - 25.10.2018
 * @Title: JournalSubscriptionsView
 * @Description: description
 *
 * Changes History
 */
@Named
@ViewScoped
public class JournalSubscriptionsView implements Serializable {

    @EJB
    private JournalSubscriptionsFacadeLocal subscriptionFacade;

    private List<JournalSubscription> subscriptions = new ArrayList<>();

    //search criterias
    private String journalNameSearchCriteria;
    Date subscriptionDateStartSearchCriteria;
    Date subscriptionDateEndSearchCriteria;

    //journal search data
    @Inject
    private JournalsView journalsView;

    public List<JournalSubscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(final List<JournalSubscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public String getJournalNameSearchCriteria() {
        return journalNameSearchCriteria;
    }

    public void setJournalNameSearchCriteria(final String journalNameSearchCriteria) {
        this.journalNameSearchCriteria = journalNameSearchCriteria;
    }

    public Date getSubscriptionDateStartSearchCriteria() {
        return subscriptionDateStartSearchCriteria;
    }

    public void setSubscriptionDateStartSearchCriteria(final Date subscriptionDateStartSearchCriteria) {
        this.subscriptionDateStartSearchCriteria = subscriptionDateStartSearchCriteria;
    }

    public Date getSubscriptionDateEndSearchCriteria() {
        return subscriptionDateEndSearchCriteria;
    }

    public void setSubscriptionDateEndSearchCriteria(final Date subscriptionDateEndSearchCriteria) {
        this.subscriptionDateEndSearchCriteria = subscriptionDateEndSearchCriteria;
    }

    public JournalsView getJournalsView() {
        return journalsView;
    }

    public void setJournalsView(final JournalsView journalsView) {
        this.journalsView = journalsView;
    }

    public void searchJournalSubscriptions() {
        setSubscriptions(subscriptionFacade.searchMySubscriptions(
                journalNameSearchCriteria, subscriptionDateStartSearchCriteria,
                subscriptionDateEndSearchCriteria));
    }

    public void subscribeToJournal(final Integer idJournal) {
        subscriptionFacade.subscribeToJournal(idJournal);
        journalsView.setCurrentJournal(journalsView.getJournalFacade().getJournalById(idJournal).get());
        journalsView.getJournals().remove(journalsView.getCurrentJournal());
    }

    public void deleteSubscription(final Integer idSubscription) {
        JournalSubscription current = subscriptionFacade.getJournalSubscriptionById(idSubscription).get();
        subscriptionFacade.removeSubscription(idSubscription);
        subscriptions.remove(current);
    }
}
