package com.jgranados.journals.journal.repository;

import com.jgranados.journals.authentication.service.AuthenticationService;
import com.jgranados.journals.journal.domain.Journal;
import com.jgranados.journals.journal.domain.Journal_;
import com.jgranados.journals.subscription.domain.JournalSubscription;
import com.jgranados.journals.subscription.domain.JournalSubscription_;
import com.jgranados.journals.user.domain.Profile;
import com.jgranados.journals.user.domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * JEE8_Tutorial-ejb
 *
 * @author jose - 17.10.2018
 * @Title: JournalRepository
 * @Description: description
 *
 * Changes History
 */
@Stateless
@LocalBean
public class JournalRepository {

    @PersistenceContext(unitName = "JEE8_Tutorial-PU")
    private EntityManager entityManager;

    @EJB
    private AuthenticationService authenticationService;

    /**
     * Method to get a journal using the id
     *
     * @param id
     * @return Optional with the journal or empty
     */
    public Optional<Journal> getJournalById(final int id) {
        return Optional.ofNullable(entityManager.find(Journal.class, id));
    }

    /**
     * Method to search journals using name and tags. If a parameter is null then it is not included in the search
     *
     * @param name
     * @param tags
     * @param ownerProfile
     * @param active
     * @return List with the journals
     */
    public List<Journal> searchJournals(
            final String name,
            final String tags,
            final Integer ownerProfile,
            final Boolean active) {
        CriteriaBuilder journalsBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Journal> query = journalsBuilder.createQuery(Journal.class);
        Root<Journal> journalsRoot = query.from(Journal.class);
        query.select(journalsRoot);

        List<Predicate> predicateList = new ArrayList<>();

        Predicate namePredicate;
        if (StringUtils.isNotEmpty(name)) {
            namePredicate = journalsBuilder.like(
                    journalsBuilder.upper(journalsRoot.get(Journal_.name)), "%" + name.toUpperCase() + "%");
            predicateList.add(namePredicate);
        }

        Predicate tagsPredicate;
        if (StringUtils.isNotEmpty(tags)) {
            tagsPredicate = journalsBuilder.like(
                    journalsBuilder.upper(journalsRoot.get(Journal_.tags)), "%" + tags.toUpperCase() + "%");
            predicateList.add(tagsPredicate);
        }

        Predicate ownerProfilePredicate;
        if (ObjectUtils.defaultIfNull(ownerProfile, 0) > 0) {
            ownerProfilePredicate = journalsBuilder.equal(
                    journalsRoot.get(Journal_.ownerProfile), entityManager.find(Profile.class, ownerProfile));
            predicateList.add(ownerProfilePredicate);
        }

        Predicate activePredicate;
        if (active != null) {
            activePredicate = journalsBuilder.equal(journalsRoot.get(Journal_.active), active);
            predicateList.add(activePredicate);
        }

        query.where(predicateList.stream().toArray(Predicate[]::new));

        return entityManager.createQuery(query).getResultList();
    }

    /**
     * Method to search journals available for subscription to the current user. If a parameter is null then it is not
     * included in the search
     *
     * @param name
     * @param tags
     * @param ownerProfile
     * @return List with the journals
     */
    public List<Journal> searchJournalsSubscriptionAvailable(
            final String name,
            final String tags,
            final Integer ownerProfile) {
        // current user
        User currentUser = authenticationService.getAuthenticatedUser().get();

        CriteriaBuilder journalsBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Journal> query = journalsBuilder.createQuery(Journal.class);
        Root<Journal> journalsRoot = query.from(Journal.class);
        query.select(journalsRoot);
        // subquery
        Subquery<Integer> subscriptionsSubquery = query.subquery(Integer.class);
        Root<JournalSubscription> subscriptionsRoot = subscriptionsSubquery.from(JournalSubscription.class);
        Path<Integer> idJournalPath = subscriptionsRoot.join(JournalSubscription_.journal).get(Journal_.idJournal);
        subscriptionsSubquery.select(idJournalPath);
        subscriptionsSubquery.where(journalsBuilder.equal(
                subscriptionsRoot.get(JournalSubscription_.user),
                currentUser));

        List<Predicate> predicateList = new ArrayList<>();

        Predicate namePredicate;
        if (StringUtils.isNotEmpty(name)) {
            namePredicate = journalsBuilder.like(
                    journalsBuilder.upper(journalsRoot.get(Journal_.name)), "%" + name.toUpperCase() + "%");
            predicateList.add(namePredicate);
        }

        Predicate tagsPredicate;
        if (StringUtils.isNotEmpty(tags)) {
            tagsPredicate = journalsBuilder.like(
                    journalsBuilder.upper(journalsRoot.get(Journal_.tags)), "%" + tags.toUpperCase() + "%");
            predicateList.add(tagsPredicate);
        }

        Predicate ownerProfilePredicate;
        if (ObjectUtils.defaultIfNull(ownerProfile, 0) > 0) {
            ownerProfilePredicate = journalsBuilder.equal(
                    journalsRoot.get(Journal_.ownerProfile),
                    entityManager.find(Profile.class, ownerProfile));
            predicateList.add(ownerProfilePredicate);
        }

        Predicate activePredicate = journalsBuilder.equal(journalsRoot.get(Journal_.active), true);
        predicateList.add(activePredicate);
        predicateList.add(journalsRoot.get(Journal_.idJournal).in(subscriptionsSubquery).not());

        query.where(predicateList.stream().toArray(Predicate[]::new));

        return entityManager.createQuery(query).getResultList();
    }
}
