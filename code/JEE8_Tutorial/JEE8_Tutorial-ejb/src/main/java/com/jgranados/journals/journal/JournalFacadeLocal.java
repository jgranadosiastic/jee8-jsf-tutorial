package com.jgranados.journals.journal;

import com.jgranados.journals.journal.domain.Journal;
import com.jgranados.journals.journalpublication.domain.JournalPublication;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.ejb.Local;

/**
 * JEE8_Tutorial-ejb
 *
 * @author jose - 23.10.2018
 * @Title: JournalFacadeLocal
 * @Description: description
 *
 * Changes History
 */
@Local
public interface JournalFacadeLocal {

    public Journal createJournal(final Journal newJournal);

    public Journal updateJournal(final Journal journalValues);

    public void deleteJournal(final Integer id);

    public JournalPublication createJournalPublication(final JournalPublication newJournalPublication,
                                                       final Integer idJournal,
                                                       final InputStream fileInputStream,
                                                       final String fileName)
            throws IOException;

    public JournalPublication updateJournalPublication(final JournalPublication journalPublicationValues,
                                                       final InputStream fileInputStream,
                                                       final String fileName)
            throws IOException;

    public void deleteJournalPublication(final Integer id);

    public List<Journal> searchMyJournals(final String name,
                                          final String tags);

    public Optional<Journal> getJournalById(final int id);

    public Optional<JournalPublication> getJournalPublicationById(final int id);

    public List<JournalPublication> searchMyJournalPublications(final Integer journal,
                                                                final Date publicationDateIni,
                                                                final Date publicationDateEnd);
}
