package com.jgranados.journals.journal;

import com.jgranados.journals.authentication.enums.RoleEnum;
import com.jgranados.journals.authentication.service.AuthenticationService;
import com.jgranados.journals.journal.domain.Journal;
//import com.jgranados.journals.journal.repository.JournalRepository;
//import com.jgranados.journals.journal.service.JournalService;
import com.jgranados.journals.journalpublication.domain.JournalPublication;
//import com.jgranados.journals.journalpublication.repository.JournalPublicationRepository;
//import com.jgranados.journals.journalpublication.service.JournalPublicationService;
import com.jgranados.journals.user.domain.User;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * JEE8_Tutorial-ejb
 *
 * @author jose - 23.10.2018
 * @Title: JournalFacade
 * @Description: description
 *
 * Changes History
 */
@Stateless
public class JournalFacade implements JournalFacadeLocal {
/*
    @EJB
    private JournalService journalService;

    @EJB
    private JournalPublicationService journalPublicationService;

    @EJB
    private JournalRepository journalRepository;

    @EJB
    private JournalPublicationRepository journalPublicationRepository;

    @EJB
    private AuthenticationService authenticationService;
*/
    @Override
    public Journal createJournal(final Journal newJournal) {
        //return journalService.createJournal(newJournal);
        return null;
    }

    @Override
    public Journal updateJournal(final Journal journalValues) {
        //return journalService.updateJournal(journalValues);
        return null;
    }

    @Override
    public void deleteJournal(final Integer id) {
        //journalService.deleteJournal(id);
    }

    @Override
    public JournalPublication createJournalPublication(final JournalPublication newJournalPublication,
                                                       final Integer idJournal,
                                                       final InputStream fileInputStream,
                                                       final String fileName)
            throws IOException {
        /*return journalPublicationService.createJournalPublication(
                newJournalPublication, idJournal, fileInputStream, fileName);*/
        return null;
    }

    @Override
    public JournalPublication updateJournalPublication(final JournalPublication journalPublicationValues,
                                                       final InputStream fileInputStream,
                                                       final String fileName)
            throws IOException {
        /*return journalPublicationService.updateJournalPublication(
                journalPublicationValues, fileInputStream, fileName);*/
        return null;
    }

    @Override
    public void deleteJournalPublication(final Integer id) {
        //journalPublicationService.deleteJournalPublication(id);
    }

    @Override
    public List<Journal> searchMyJournals(final String name,
                                          final String tags) {
        /*User user = authenticationService.getAuthenticatedUser().get();
        Integer idProfile = user.getProfile().getIdProfile();
        if (user.getUserRole().equals(RoleEnum.PUBLIC)) {
            return journalRepository.searchJournalsSubscriptionAvailable(name, tags, null);
        } else {
            return journalRepository.searchJournals(name, tags, idProfile, null);
        }*/
        return null;
    }

    @Override
    public Optional<Journal> getJournalById(final int id) {
        //return journalRepository.getJournalById(id);
        return null;
    }

    @Override
    public Optional<JournalPublication> getJournalPublicationById(final int id) {
        //return journalPublicationRepository.getJournalPublicationById(id);
        return null;
    }

    @Override
    public List<JournalPublication> searchMyJournalPublications(final Integer journal,
                                                                final Date publicationDateIni,
                                                                final Date publicationDateEnd) {
        /*User user = authenticationService.getAuthenticatedUser().get();
        Integer idProfile = user.getProfile().getIdProfile();
        if (user.getUserRole().equals(RoleEnum.PUBLIC)) {
            idProfile = null;
        }
        return journalPublicationRepository.searchJournalPublications(
                journal, publicationDateIni, publicationDateEnd, idProfile);*/
        return null;
    }

}
