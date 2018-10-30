package com.jgranados.journals.journal.service;

import com.jgranados.journals.authentication.service.AuthenticationService;
import com.jgranados.journals.journal.domain.Journal;
import com.jgranados.journals.journal.repository.JournalRepository;
import com.jgranados.journals.user.domain.User;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * JEE8_Tutorial-ejb
 *
 * @author jose - 23.10.2018
 * @Title: JournalService
 * @Description: description
 *
 * Changes History
 */
@Stateless
@LocalBean
public class JournalService {

    @PersistenceContext(unitName = "JEE8_Tutorial-PU")
    private EntityManager em;

    @EJB
    AuthenticationService authenticationService;

    @EJB
    JournalRepository journalRepository;

    /**
     * Method to create a new journal
     *
     * @param newJournal
     * @return the created journal
     */
    public Journal createJournal(final Journal newJournal) {
        User creator = authenticationService.getAuthenticatedUser().get();
        newJournal.setOwnerProfile(creator.getProfile());
        newJournal.setActive(true);
        em.persist(newJournal);
        return newJournal;
    }

    /**
     * Metod to update the information of a journal
     *
     * @param journalValues
     * @return the updated journal
     */
    public Journal updateJournal(final Journal journalValues) {
        Journal journal = journalRepository.getJournalById(journalValues.getIdJournal()).get();
        journal.setAbout(journalValues.getAbout());
        journal.setActive(journalValues.isActive());
        journal.setName(journalValues.getName());
        journal.setTags(journalValues.getTags());
        return journal;
    }

    /**
     * Method to delete the journal and its publications
     *
     * @param id
     */
    public void deleteJournal(final Integer id) {
        //journalPublications removed in cascade also
        em.remove(journalRepository.getJournalById(id).get());
    }
}
