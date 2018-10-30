package com.jgranados.journals.journalpublication.service;

import com.jgranados.journals.authentication.service.AuthenticationService;
import com.jgranados.journals.journal.domain.Journal;
import com.jgranados.journals.journal.repository.JournalRepository;
import com.jgranados.journals.journalpublication.domain.JournalPublication;
import com.jgranados.journals.journalpublication.repository.JournalPublicationRepository;
import com.jgranados.journals.user.domain.User;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * JEE8_Tutorial-ejb
 *
 * @author jose - 24.10.2018
 * @Title: JournalPublicationService
 * @Description: description
 *
 * Changes History
 */
@Stateless
@LocalBean
public class JournalPublicationService {

    @PersistenceContext(unitName = "JEE8_Tutorial-PU")
    private EntityManager entityManager;

    @EJB
    AuthenticationService authenticationService;

    @EJB
    JournalRepository journalRepository;

    @EJB
    JournalPublicationRepository journalPublicationRepository;

    /**
     * Method do create a new journal publication
     *
     * @param newJournalPublication
     * @param idJournal
     * @param fileInputStream
     * @param fileName
     * @return the created journal publication
     * @throws java.io.IOException
     */
    public JournalPublication createJournalPublication(final JournalPublication newJournalPublication,
                                                       final Integer idJournal,
                                                       final InputStream fileInputStream,
                                                       final String fileName)
            throws IOException {
        User creator = authenticationService.getAuthenticatedUser().get();
        newJournalPublication.setPublisherProfile(creator.getProfile());
        newJournalPublication.setPublicationDate(new Date());
        if (fileInputStream != null && StringUtils.isNotEmpty(fileName)) {
            newJournalPublication.setFileName(fileName);
            newJournalPublication.setContent((IOUtils.toByteArray(fileInputStream)));
        }
        Journal parentJournal = journalRepository.getJournalById(idJournal).get();
        newJournalPublication.setJournal(parentJournal);
        // add publication by cascade
        parentJournal.getJournalPublicationsCollection().add(newJournalPublication);
        entityManager.flush();
        return newJournalPublication;
    }

    /**
     * Method to update de information of a journal publication
     *
     * @param journalPublicationValues
     * @param fileInputStream
     * @param fileName
     * @return the updated journal publication
     * @throws java.io.IOException
     */
    public JournalPublication updateJournalPublication(final JournalPublication journalPublicationValues,
                                                       final InputStream fileInputStream,
                                                       final String fileName)
            throws IOException {
        JournalPublication journalPublication = journalPublicationRepository
                .getJournalPublicationById(journalPublicationValues.getIdJournalPublication()).get();
        journalPublication.setDescription(journalPublicationValues.getDescription());
        if (fileInputStream != null && StringUtils.isNotEmpty(fileName)) {
            journalPublication.setFileName(fileName);
            journalPublication.setContent(IOUtils.toByteArray(fileInputStream));
        }
        return journalPublication;
    }

    /**
     * Method to delete the journal publication
     *
     * @param id
     */
    public void deleteJournalPublication(final Integer id) {
        JournalPublication journalPublication = journalPublicationRepository.getJournalPublicationById(id).get();
        Journal journalParent = journalPublication.getJournal();
        journalParent.getJournalPublicationsCollection().remove(journalPublication);
        entityManager.merge(journalParent);
    }
}
