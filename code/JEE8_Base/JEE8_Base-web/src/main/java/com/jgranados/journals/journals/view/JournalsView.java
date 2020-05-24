package com.jgranados.journals.journals.view;

import com.jgranados.journals.journal.JournalFacadeLocal;
import com.jgranados.journals.journal.domain.Journal;
import com.jgranados.journals.utils.MessageUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

/**
 * JEE8_Tutorial-web
 *
 * @author jose - 24.10.2018
 * @Title: JournalsView
 * @Description: description
 *
 * Changes History
 */
@Named
@ViewScoped
public class JournalsView implements Serializable {

    private static final String JOURNAL_CREATED_KEY = "JournalCreated";
    private static final String JOURNAL_UPDATED_KEY = "JournalUpdated";

    @EJB
    private JournalFacadeLocal journalFacade;

    List<Journal> journals = new ArrayList<>();
    //search criterias
    String nameSearchCriteria;
    String tagSearchCriteria;

    //new or selected journal
    Journal currentJournal;

    public JournalFacadeLocal getJournalFacade() {
        return journalFacade;
    }

    public void setJournalFacade(final JournalFacadeLocal journalFacade) {
        this.journalFacade = journalFacade;
    }

    public List<Journal> getJournals() {
        return journals;
    }

    public void setJournals(final List<Journal> journals) {
        this.journals = journals;
    }

    public String getNameSearchCriteria() {
        return nameSearchCriteria;
    }

    public void setNameSearchCriteria(final String nameSearchCriteria) {
        this.nameSearchCriteria = nameSearchCriteria;
    }

    public String getTagSearchCriteria() {
        return tagSearchCriteria;
    }

    public void setTagSearchCriteria(final String tagSearchCriteria) {
        this.tagSearchCriteria = tagSearchCriteria;
    }

    public Journal getCurrentJournal() {
        if (currentJournal == null) {
            currentJournal = new Journal();
        }
        return currentJournal;
    }

    public void setCurrentJournal(final Journal currentJournal) {
        this.currentJournal = currentJournal;
    }

    public void searchJournals() {
        setJournals(journalFacade.searchMyJournals(nameSearchCriteria, tagSearchCriteria));
    }

    public void saveChanges(final String modalIdToClose) {
        if (currentJournal.getIdJournal() != null) {
            journalFacade.updateJournal(currentJournal);
            MessageUtils.addSuccessLocalizedMessage(JOURNAL_UPDATED_KEY);
        } else {
            journalFacade.createJournal(currentJournal);
            MessageUtils.addSuccessLocalizedMessage(JOURNAL_CREATED_KEY);
        }
        clearCurrentJournal();
        PrimeFaces.current().executeScript("PF('" + modalIdToClose + "').hide()");
    }

    public void deleteJournal(final Integer idJournal) {
        currentJournal = journalFacade.getJournalById(idJournal).get();
        journals.remove(currentJournal);
        journalFacade.deleteJournal(idJournal);
        clearCurrentJournal();
    }

    public void clearCurrentJournal() {
        setCurrentJournal(null);
    }

    public void cleanCriteria() {
        nameSearchCriteria = "";
        tagSearchCriteria = "";
    }
}
