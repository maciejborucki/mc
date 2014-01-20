/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.mi.mcloud.selfcare.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bor
 */
@Entity
@Table(name = "complaint_history", catalog = "mcloud", schema = "mcloud")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComplaintHistory.findAll", query = "SELECT c FROM ComplaintHistory c"),
    @NamedQuery(name = "ComplaintHistory.findById", query = "SELECT c FROM ComplaintHistory c WHERE c.id = :id"),
    @NamedQuery(name = "ComplaintHistory.findByDateCreated", query = "SELECT c FROM ComplaintHistory c WHERE c.dateCreated = :dateCreated"),
    @NamedQuery(name = "ComplaintHistory.findByDateAcknowledged", query = "SELECT c FROM ComplaintHistory c WHERE c.dateAcknowledged = :dateAcknowledged"),
    @NamedQuery(name = "ComplaintHistory.findByDateDue", query = "SELECT c FROM ComplaintHistory c WHERE c.dateDue = :dateDue"),
    @NamedQuery(name = "ComplaintHistory.findByDateClosed", query = "SELECT c FROM ComplaintHistory c WHERE c.dateClosed = :dateClosed"),
    @NamedQuery(name = "ComplaintHistory.findByContents", query = "SELECT c FROM ComplaintHistory c WHERE c.contents = :contents"),
    @NamedQuery(name = "ComplaintHistory.findByTrackList", query = "SELECT c FROM ComplaintHistory c WHERE c.trackList = :trackList"),
    @NamedQuery(name = "ComplaintHistory.findByHistoryEntryCreated", query = "SELECT c FROM ComplaintHistory c WHERE c.historyEntryCreated = :historyEntryCreated")})
public class ComplaintHistory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_created", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateCreated;
    @Column(name = "date_acknowledged")
    @Temporal(TemporalType.DATE)
    private Date dateAcknowledged;
    @Column(name = "date_due")
    @Temporal(TemporalType.DATE)
    private Date dateDue;
    @Column(name = "date_closed")
    @Temporal(TemporalType.DATE)
    private Date dateClosed;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(nullable = false, length = 2147483647)
    private String contents;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "track_list", nullable = false, length = 2147483647)
    private String trackList;
    @Column(name = "history_entry_created")
    @Temporal(TemporalType.DATE)
    private Date historyEntryCreated;
    @JoinColumn(name = "status", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Status status;
    @JoinColumn(name = "priority", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Priority priority;
    @JoinColumn(name = "creator", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private PlatformUser creator;
    @JoinColumn(name = "complaint_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Complaint complaintId;

    public ComplaintHistory() {
    }

    public ComplaintHistory(Long id) {
        this.id = id;
    }

    public ComplaintHistory(Long id, Date dateCreated, String contents, String trackList) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.contents = contents;
        this.trackList = trackList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateAcknowledged() {
        return dateAcknowledged;
    }

    public void setDateAcknowledged(Date dateAcknowledged) {
        this.dateAcknowledged = dateAcknowledged;
    }

    public Date getDateDue() {
        return dateDue;
    }

    public void setDateDue(Date dateDue) {
        this.dateDue = dateDue;
    }

    public Date getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(Date dateClosed) {
        this.dateClosed = dateClosed;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getTrackList() {
        return trackList;
    }

    public void setTrackList(String trackList) {
        this.trackList = trackList;
    }

    public Date getHistoryEntryCreated() {
        return historyEntryCreated;
    }

    public void setHistoryEntryCreated(Date historyEntryCreated) {
        this.historyEntryCreated = historyEntryCreated;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public PlatformUser getCreator() {
        return creator;
    }

    public void setCreator(PlatformUser creator) {
        this.creator = creator;
    }

    public Complaint getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(Complaint complaintId) {
        this.complaintId = complaintId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComplaintHistory)) {
            return false;
        }
        ComplaintHistory other = (ComplaintHistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.mi.mcloud.selfcare.entity.ComplaintHistory[ id=" + id + " ]";
    }
    
}
