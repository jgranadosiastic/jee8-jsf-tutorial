package com.jgranados.journals.journal.domain;

import com.jgranados.journals.journalpublication.domain.JournalPublication;
import com.jgranados.journals.user.domain.Profile;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * JEE Tutorial
 *
 * @author jose
 * @Title: Journal
 * @Description: description
 *
 * Changes History
 */
@Entity
@Table(name = "Journal")
@NamedQueries({
    @NamedQuery(name = "Journal.findAll", query = "SELECT j FROM Journal j")})
public class Journal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idJournal")
    private Integer idJournal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;
    @Size(max = 200)
    @Column(name = "about")
    private String about;
    @Lob
    @Column(name = "image")
    private byte[] image;
    @Size(max = 200)
    @Column(name = "tags")
    private String tags;
    @Basic(optional = false)
    @NotNull
    @Column(name = "active")
    private boolean active;
    @JoinColumn(name = "owner_profile", referencedColumnName = "idProfile")
    @ManyToOne(optional = false)
    private Profile ownerProfile;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "journal", orphanRemoval = true)
    private Collection<JournalPublication> journalPublicationsCollection;
    @Transient
    private int publicationsCount;

    public Journal() {
    }

    public Journal(Integer idJournal) {
        this.idJournal = idJournal;
    }

    public Journal(Integer idJournal,
                   String name,
                   boolean active) {
        this.idJournal = idJournal;
        this.name = name;
        this.active = active;
    }

    @PostLoad
    private void postLoad() {
        if (this.journalPublicationsCollection != null) {
            publicationsCount = journalPublicationsCollection.size();
        } else {
            publicationsCount = 0;
        }
    }

    public Integer getIdJournal() {
        return idJournal;
    }

    public void setIdJournal(Integer idJournal) {
        this.idJournal = idJournal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Profile getOwnerProfile() {
        return ownerProfile;
    }

    public void setOwnerProfile(Profile ownerProfile) {
        this.ownerProfile = ownerProfile;
    }

    public Collection<JournalPublication> getJournalPublicationsCollection() {
        return journalPublicationsCollection;
    }

    public void setJournalPublicationsCollection(Collection<JournalPublication> journalPublicationsCollection) {
        this.journalPublicationsCollection = journalPublicationsCollection;
    }

    @Transient
    public int getPublicationsCount() {
        return publicationsCount;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idJournal != null ? idJournal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Journal)) {
            return false;
        }
        Journal other = (Journal) object;
        if ((this.idJournal == null && other.idJournal != null) || (this.idJournal != null && !this.idJournal.equals(other.idJournal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Journal[ idJournal=" + idJournal + " ]";
    }

}
