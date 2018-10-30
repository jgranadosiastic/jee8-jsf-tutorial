package com.jgranados.journals.subscription.repository;

import com.jgranados.journals.authentication.service.AuthenticationService;
import com.jgranados.journals.journal.domain.Journal_;
import com.jgranados.journals.subscription.domain.JournalSubscription;
import com.jgranados.journals.subscription.domain.JournalSubscription_;
import com.jgranados.journals.user.domain.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;

/**
 * JEE8_Tutorial-ejb
 *
 * @author jose - 18.10.2018
 * @Title: SubscriptionRepository
 * @Description: description
 *
 * Changes History
 */
@Stateless
@LocalBean
public class JournalSubscriptionRepository {

    @PersistenceContext(unitName = "JEE8_Tutorial-PU")
    private EntityManager entityManager;

    @EJB
    private AuthenticationService authenticationService;

    public Optional<JournalSubscription> getJournalSubscriptionById(final Integer idJournalSubscription) {
        return Optional.ofNullable(entityManager.find(JournalSubscription.class, idJournalSubscription));
    }

    public List<JournalSubscription> searchMySubscriptions(
            final String journalName,
            final Date subscriptionDateIni,
            final Date subscriptionDateEnd) {
        CriteriaBuilder journalSubscriptionBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<JournalSubscription> query = journalSubscriptionBuilder.createQuery(JournalSubscription.class);
        Root<JournalSubscription> journalSubscriptionRoot = query.from(JournalSubscription.class);
        query.select(journalSubscriptionRoot);

        List<Predicate> predicateList = new ArrayList<>();

        Predicate journalPredicate;
        if (StringUtils.isNotEmpty(journalName)) {
            journalPredicate = journalSubscriptionBuilder
                    .like(journalSubscriptionBuilder
                            .upper(journalSubscriptionRoot.get(JournalSubscription_.journal).get(Journal_.name)),
                            "%" + journalName + "%");
            predicateList.add(journalPredicate);
        }

        Predicate subscriptionDatePredicate;
        if (subscriptionDateIni != null && subscriptionDateEnd != null) {
            subscriptionDatePredicate = journalSubscriptionBuilder.between(
                    journalSubscriptionRoot.get(JournalSubscription_.subscriptionDate),
                    subscriptionDateIni,
                    subscriptionDateEnd);
            predicateList.add(subscriptionDatePredicate);
        }

        Predicate subscriberPredicate;
        User currentUser = authenticationService.getAuthenticatedUser().get();
        subscriberPredicate = journalSubscriptionBuilder.equal(
                journalSubscriptionRoot.get(JournalSubscription_.user),
                entityManager.find(User.class, currentUser.getIdUser()));
        predicateList.add(subscriberPredicate);

        query.where(predicateList.stream().toArray(Predicate[]::new));

        return entityManager.createQuery(query).getResultList();
    }
}
