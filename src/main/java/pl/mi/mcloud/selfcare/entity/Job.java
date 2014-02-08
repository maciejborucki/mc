/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.mi.mcloud.selfcare.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author bor
 */
@Entity
@Table(catalog = "mcloud", schema = "mcloud")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Job.findAll", query = "SELECT j FROM Job j"),
    @NamedQuery(name = "Job.findById", query = "SELECT j FROM Job j WHERE j.id = :id"),
    @NamedQuery(name = "Job.findByDateCreated", query = "SELECT j FROM Job j WHERE j.dateCreated = :dateCreated"),
    @NamedQuery(name = "Job.findByDateAcknowledged", query = "SELECT j FROM Job j WHERE j.dateAcknowledged = :dateAcknowledged"),
    @NamedQuery(name = "Job.findByDateDue", query = "SELECT j FROM Job j WHERE j.dateDue = :dateDue"),
    @NamedQuery(name = "Job.findByDateClosed", query = "SELECT j FROM Job j WHERE j.dateClosed = :dateClosed"),
    @NamedQuery(name = "Job.findByContents", query = "SELECT j FROM Job j WHERE j.contents = :contents"),
    @NamedQuery(name = "Job.findByTrackList", query = "SELECT j FROM Job j WHERE j.trackList = :trackList"),
    @NamedQuery(name = "Job.findByPlannedWorkStart", query = "SELECT j FROM Job j WHERE j.plannedWorkStart = :plannedWorkStart"),
    @NamedQuery(name = "Job.findByPlannedWorkEnd", query = "SELECT j FROM Job j WHERE j.plannedWorkEnd = :plannedWorkEnd"),
    @NamedQuery(name = "Job.findByCreator", query = "SELECT j FROM Job j WHERE j.creator = :creator")
})
public class Job implements Serializable {
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
    @Column(name = "contents", nullable = false, length = 2147483647)
    private String contents;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "track_list", nullable = false, length = 2147483647)
    private String trackList;
    @Column(name = "planned_work_start")
    @Temporal(TemporalType.DATE)
    private Date plannedWorkStart;
    @Column(name = "planned_work_end")
    @Temporal(TemporalType.DATE)
    private Date plannedWorkEnd;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jobId", fetch = FetchType.EAGER)
    private List<JobHistory> jobHistoryList;
    @JoinColumn(name = "status", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Status status;
    @JoinColumn(name = "priority", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Priority priority;
    @JoinColumn(name = "creator", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private PlatformUser creator;
    @JoinColumn(name = "assignee", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private PlatformUser assignee;
    @JoinColumn(name = "service", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private PlatformService service;
    @JoinColumn(name = "module", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private PlatformModule module;
    @JoinColumn(name = "component", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private PlatformComponent component;
    @JoinColumn(name = "complaint", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Complaint complaint;

    public Job() {
    }

    public Job(Long id) {
        this.id = id;
    }

    public Job(Long id, Date dateCreated, String contents, String trackList) {
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

    public Date getPlannedWorkStart() {
        return plannedWorkStart;
    }

    public void setPlannedWorkStart(Date plannedWorkStart) {
        this.plannedWorkStart = plannedWorkStart;
    }

    public Date getPlannedWorkEnd() {
        return plannedWorkEnd;
    }

    public void setPlannedWorkEnd(Date plannedWorkEnd) {
        this.plannedWorkEnd = plannedWorkEnd;
    }

    @XmlTransient
    @JsonIgnore
    public List<JobHistory> getJobHistoryList() {
        return jobHistoryList;
    }

    public void setJobHistoryList(List<JobHistory> jobHistoryList) {
        this.jobHistoryList = jobHistoryList;
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

    public PlatformUser getAssignee() {
        return assignee;
    }

    public void setAssignee(PlatformUser assignee) {
        this.assignee = assignee;
    }

    public PlatformService getService() {
        return service;
    }

    public void setService(PlatformService service) {
        this.service = service;
    }

    public PlatformModule getModule() {
        return module;
    }

    public void setModule(PlatformModule module) {
        this.module = module;
    }

    public PlatformComponent getComponent() {
        return component;
    }

    public void setComponent(PlatformComponent component) {
        this.component = component;
    }

    public Complaint getComplaint() {
        return complaint;
    }

    public void setComplaint(Complaint complaint) {
        this.complaint = complaint;
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
        if (!(object instanceof Job)) {
            return false;
        }
        Job other = (Job) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.mi.mcloud.selfcare.entity.Job[ id=" + id + " ]";
    }
    
}
