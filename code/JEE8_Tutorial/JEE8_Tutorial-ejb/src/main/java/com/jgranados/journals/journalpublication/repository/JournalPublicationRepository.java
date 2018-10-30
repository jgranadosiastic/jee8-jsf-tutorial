package com.jgranados.journals.journalpublication.repository;

import com.jgranados.journals.journal.domain.Journal;
import com.jgranados.journals.journalpublication.domain.JournalPublication;
import com.jgranados.journals.journalpublication.domain.JournalPublication_;
import com.jgranados.journals.user.domain.Profile;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.ObjectUtils;

/**
 * JEE8_Tutorial-ejb
 *
 * @author jose - 24.10.2018
 * @Title: JournalPublicationRepository
 * @Description: description
 *
 * Changes History
 */
@Stateless
@LocalBean
public class JournalPublicationRepository {

    @PersistenceContext(unitName = "JEE8_Tutorial-PU")
    private EntityManager entityManager;

    /**
     * Method to get a journal publication using the id
     *
     * @param id
     * @return the jurnal or null
     */
    public Optional<JournalPublication> getJournalPublicationById(final int id) {
        return Optional.ofNullable(entityManager.find(JournalPublication.class, id));
    }

    /**
     * Method to search the journal publications of a journal
     *
     * @param journal
     * @param publicationDateIni
     * @param publicationDateEnd
     * @param ownerProfile
     * @return The list with the publications
     */
    public List<JournalPublication> searchJournalPublications(
            final Integer journal,
            final Date publicationDateIni,
            final Date publicationDateEnd,
            final Integer ownerProfile) {
        CriteriaBuilder journalPublicationsBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<JournalPublication> query = journalPublicationsBuilder.createQuery(JournalPublication.class);
        Root<JournalPublication> journalPublicationsRoot = query.from(JournalPublication.class);
        query.select(journalPublicationsRoot);

        List<Predicate> predicateList = new ArrayList<>();

        Predicate journalPredicate;
        if (ObjectUtils.defaultIfNull(journal, 0) > 0) {
            journalPredicate = journalPublicationsBuilder.equal(
                    journalPublicationsRoot.get(JournalPublication_.journal),
                    entityManager.find(Journal.class, journal));
            predicateList.add(journalPredicate);
        }

        Predicate publicationDatePredicate;
        if (publicationDateIni != null && publicationDateEnd != null) {
            publicationDatePredicate = journalPublicationsBuilder.between(
                    journalPublicationsRoot.get(JournalPublication_.publicationDate),
                    publicationDateIni,
                    publicationDateEnd);
            predicateList.add(publicationDatePredicate);
        }

        Predicate ownerProfilePredicate;
        if (ObjectUtils.defaultIfNull(ownerProfile, 0) > 0) {
            ownerProfilePredicate = journalPublicationsBuilder.equal(
                    journalPublicationsRoot.get(JournalPublication_.publisherProfile),
                    entityManager.find(Profile.class, ownerProfile));
            predicateList.add(ownerProfilePredicate);
        }

        query.where(predicateList.stream().toArray(Predicate[]::new));

        return entityManager.createQuery(query).getResultList();
    }

}
