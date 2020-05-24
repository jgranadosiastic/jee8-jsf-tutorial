package com.jgranados.journals.subscription.domain;

import com.jgranados.journals.journal.domain.Journal;
import com.jgranados.journals.user.domain.User;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * JEE Tutorial
 *
 * @author jose
 * @Title: JournalSubscription
 * @Description: description
 *
 * Changes History
 */
@Entity
@Table(name = "JournalSubscription")
@NamedQueries({
    @NamedQuery(name = "JournalSubscription.findAll", query = "SELECT j FROM JournalSubscription j")})
public class JournalSubscription implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idJournalSubscription")
    private Integer idJournalSubscription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "subscription_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date subscriptionDate;
    @JoinColumn(name = "journal", referencedColumnName = "idJournal")
    @ManyToOne(optional = false)
    private Journal journal;
    @JoinColumn(name = "user", referencedColumnName = "idUser")
    @ManyToOne(optional = false)
    private User user;

    public JournalSubscription() {
    }

    public JournalSubscription(Integer idJournalSubscription) {
        this.idJournalSubscription = idJournalSubscription;
    }

    public JournalSubscription(Integer idJournalSubscription,
                               Date subscriptionDate) {
        this.idJournalSubscription = idJournalSubscription;
        this.subscriptionDate = subscriptionDate;
    }

    public Integer getIdJournalSubscription() {
        return idJournalSubscription;
    }

    public void setIdJournalSubscription(Integer idJournalSubscription) {
        this.idJournalSubscription = idJournalSubscription;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idJournalSubscription != null ? idJournalSubscription.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JournalSubscription)) {
            return false;
        }
        JournalSubscription other = (JournalSubscription) object;
        if ((this.idJournalSubscription == null && other.idJournalSubscription != null) || (this.idJournalSubscription != null && !this.idJournalSubscription.equals(other.idJournalSubscription))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "JournalSubscription[ idJournalSubscription=" + idJournalSubscription + " ]";
    }

}
