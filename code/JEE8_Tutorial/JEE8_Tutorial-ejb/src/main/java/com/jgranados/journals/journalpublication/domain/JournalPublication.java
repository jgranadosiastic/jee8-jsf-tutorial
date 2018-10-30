package com.jgranados.journals.journalpublication.domain;

import com.jgranados.journals.journal.domain.Journal;
import com.jgranados.journals.user.domain.Profile;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * JEE Tutorial
 *
 * @author jose
 * @Title: JournalPublication
 * @Description: description
 *
 * Changes History
 */
@Entity
@Table(name = "JournalPublication")
@NamedQueries({
    @NamedQuery(name = "JournalPublication.findAll", query = "SELECT j FROM JournalPublication j")})
public class JournalPublication implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idJournalPublication")
    private Integer idJournalPublication;
    @Basic(optional = false)
    @NotNull
    @Column(name = "publication_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date publicationDate;
    @Lob
    @Column(name = "content")
    private byte[] content;
    @Size(max = 200)
    @Column(name = "description")
    private String description;
    @Size(max = 50)
    @Column(name = "file_name")
    private String fileName;
    @JoinColumn(name = "journal", referencedColumnName = "idJournal")
    @ManyToOne(optional = false)
    private Journal journal;
    @JoinColumn(name = "publisher_profile", referencedColumnName = "idProfile")
    @ManyToOne(optional = false)
    private Profile publisherProfile;

    public JournalPublication() {
    }

    public JournalPublication(Integer idJournalPublication) {
        this.idJournalPublication = idJournalPublication;
    }

    public JournalPublication(Integer idJournalPublication,
                              Date publicationDate) {
        this.idJournalPublication = idJournalPublication;
        this.publicationDate = publicationDate;
    }

    public Integer getIdJournalPublication() {
        return idJournalPublication;
    }

    public void setIdJournalPublication(Integer idJournalPublication) {
        this.idJournalPublication = idJournalPublication;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    public Profile getPublisherProfile() {
        return publisherProfile;
    }

    public void setPublisherProfile(Profile publisherProfile) {
        this.publisherProfile = publisherProfile;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idJournalPublication != null ? idJournalPublication.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JournalPublication)) {
            return false;
        }
        JournalPublication other = (JournalPublication) object;
        if ((this.idJournalPublication == null && other.idJournalPublication != null) || (this.idJournalPublication != null && !this.idJournalPublication.equals(other.idJournalPublication))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "JournalPublication[ idJournalPublication=" + idJournalPublication + " ]";
    }

}
