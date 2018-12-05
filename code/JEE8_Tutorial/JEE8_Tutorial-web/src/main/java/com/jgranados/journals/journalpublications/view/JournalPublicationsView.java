package com.jgranados.journals.journalpublications.view;

import com.jgranados.journals.journal.JournalFacadeLocal;
import com.jgranados.journals.journal.domain.Journal;
import com.jgranados.journals.journalpublication.domain.JournalPublication;
import com.jgranados.journals.utils.MessageUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;

/**
 * JEE8_Tutorial-web
 *
 * @author jose - 25.10.2018
 * @Title: JournalPublicationsView
 * @Description: description
 *
 * Changes History
 */
@Named
@ViewScoped
public class JournalPublicationsView implements Serializable {

    private static final String PUBLICATION_CREATED_KEY = "PublicationCreated";
    private static final String PUBLICATION_UPDATED_KEY = "PublicationUpdated";

    @EJB
    private JournalFacadeLocal journalFacade;

    private List<JournalPublication> publications = new ArrayList<>();

    //search criterias
    private Date publicationDateStartSearchCriteria;
    private Date publicationDateEndSearchCriteria;

    //new or selecter publication
    private JournalPublication currentPublication;
    private UploadedFile fileInputStream;

    //current journal data
    private Integer idCurrentJournal;
    private Journal currentJournal;

    public List<JournalPublication> getPublications() {
        return publications;
    }

    public void setPublications(final List<JournalPublication> publications) {
        this.publications = publications;
    }

    public Date getPublicationDateStartSearchCriteria() {
        return publicationDateStartSearchCriteria;
    }

    public void setPublicationDateStartSearchCriteria(final Date publicationDateStartSearchCriteria) {
        this.publicationDateStartSearchCriteria = publicationDateStartSearchCriteria;
    }

    public Date getPublicationDateEndSearchCriteria() {
        return publicationDateEndSearchCriteria;
    }

    public void setPublicationDateEndSearchCriteria(final Date publicationDateEndSearchCriteria) {
        this.publicationDateEndSearchCriteria = publicationDateEndSearchCriteria;
    }

    public JournalPublication getCurrentPublication() {
        if (currentPublication == null) {
            currentPublication = new JournalPublication();
        }
        return currentPublication;
    }

    public void setCurrentPublication(final JournalPublication currentPublication) {
        this.currentPublication = currentPublication;
    }

    public UploadedFile getFileInputStream() {
        return fileInputStream;
    }

    public void setFileInputStream(final UploadedFile fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

    public Journal getCurrentJournal() {
        return currentJournal;
    }

    public void setCurrentJournal(final Journal currentJournal) {
        this.currentJournal = currentJournal;
    }

    public Integer getIdCurrentJournal() {
        return idCurrentJournal;
    }

    public void setIdCurrentJournal(final Integer idCurrentJournal) {
        this.idCurrentJournal = idCurrentJournal;
    }

    public void loadCurrentJournal() {
        if (idCurrentJournal != null) {
            setCurrentJournal(journalFacade.getJournalById(idCurrentJournal).get());
        }
    }

    public void searchJournalPublications() {
        setPublications(journalFacade.searchMyJournalPublications(
                idCurrentJournal, publicationDateStartSearchCriteria, publicationDateEndSearchCriteria));
    }

    public void handleFileUpload(final FileUploadEvent event) {
        fileInputStream = event.getFile();
    }

    public void saveChanges(final String modalIdToClose) {
        try {
            if (currentPublication.getIdJournalPublication() != null) {
                journalFacade.updateJournalPublication(currentPublication,
                        isValidImageStream() ? fileInputStream.getInputstream() : null,
                        isValidImageStream() ? fileInputStream.getFileName() : null);
                if (isValidImageStream()) {
                    currentPublication.setFileName(fileInputStream.getFileName());
                }
                clearCurrentJournalPublication();
                PrimeFaces.current().executeScript("PF('" + modalIdToClose + "').hide()");
                MessageUtils.addSuccessLocalizedMessage(PUBLICATION_UPDATED_KEY);
            } else if (!isValidImageStream()) {
                MessageUtils.addErrorLocalizedMessage("filerequired");
            } else {
                journalFacade.createJournalPublication(currentPublication, idCurrentJournal, fileInputStream.getInputstream(), fileInputStream.getFileName());
                clearCurrentJournalPublication();
                PrimeFaces.current().executeScript("PF('" + modalIdToClose + "').hide()");
                MessageUtils.addSuccessLocalizedMessage(PUBLICATION_CREATED_KEY);
            }
        } catch (Exception e) {
            //LOG
            System.out.println("error");
        }
    }

    public DefaultStreamedContent download(final JournalPublication publication) {
        DefaultStreamedContent file = new DefaultStreamedContent(
                new ByteArrayInputStream(publication.getContent()), "application/pdf", publication.getFileName());
        return file;
    }

    public void deleteJournalPublication(final Integer idPublication) {
        currentPublication = journalFacade.getJournalPublicationById(idPublication).get();
        publications.remove(currentPublication);
        journalFacade.deleteJournalPublication(idPublication);
        clearCurrentJournalPublication();
    }

    public void clearCurrentJournalPublication() {
        setCurrentPublication(null);
        setFileInputStream(null);
    }

    public boolean isValidImageStream()
            throws IOException {
        return fileInputStream != null && fileInputStream.getInputstream() != null;
    }

    public void cleanCriteria() {
        publicationDateStartSearchCriteria = null;
        publicationDateEndSearchCriteria = null;
    }
}
