/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.mi.mcloud.selfcare.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
    @NamedQuery(name = "Priority.findAll", query = "SELECT p FROM Priority p"),
    @NamedQuery(name = "Priority.findById", query = "SELECT p FROM Priority p WHERE p.id = :id"),
    @NamedQuery(name = "Priority.findByPriorityName", query = "SELECT p FROM Priority p WHERE p.priorityName = :priorityName")})
public class Priority implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "priority_name", nullable = false, length = 2147483647)
    private String priorityName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "priority", fetch = FetchType.EAGER)
    private List<ComplaintHistory> complaintHistoryList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "priority", fetch = FetchType.EAGER)
    private List<JobHistory> jobHistoryList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "priority", fetch = FetchType.EAGER)
    private List<Job> jobList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "priority", fetch = FetchType.EAGER)
    private List<Complaint> complaintList;

    public Priority() {
    }

    public Priority(Long id) {
        this.id = id;
    }

    public Priority(Long id, String priorityName) {
        this.id = id;
        this.priorityName = priorityName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPriorityName() {
        return priorityName;
    }

    public void setPriorityName(String priorityName) {
        this.priorityName = priorityName;
    }

    @XmlTransient
    @JsonIgnore
    public List<ComplaintHistory> getComplaintHistoryList() {
        return complaintHistoryList;
    }

    public void setComplaintHistoryList(List<ComplaintHistory> complaintHistoryList) {
        this.complaintHistoryList = complaintHistoryList;
    }

    @XmlTransient
    @JsonIgnore
    public List<JobHistory> getJobHistoryList() {
        return jobHistoryList;
    }

    public void setJobHistoryList(List<JobHistory> jobHistoryList) {
        this.jobHistoryList = jobHistoryList;
    }

    @XmlTransient
    @JsonIgnore
    public List<Job> getJobList() {
        return jobList;
    }

    public void setJobList(List<Job> jobList) {
        this.jobList = jobList;
    }

    @XmlTransient
    @JsonIgnore
    public List<Complaint> getComplaintList() {
        return complaintList;
    }

    public void setComplaintList(List<Complaint> complaintList) {
        this.complaintList = complaintList;
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
        if (!(object instanceof Priority)) {
            return false;
        }
        Priority other = (Priority) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.mi.mcloud.selfcare.entity.Priority[ id=" + id + " ]";
    }
    
}
