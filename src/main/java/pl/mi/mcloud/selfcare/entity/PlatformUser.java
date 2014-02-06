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
@Table(name = "platform_user", catalog = "mcloud", schema = "mcloud")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlatformUser.findAll", query = "SELECT p FROM PlatformUser p"),
    @NamedQuery(name = "PlatformUser.findById", query = "SELECT p FROM PlatformUser p WHERE p.id = :id"),
    @NamedQuery(name = "PlatformUser.findByUsername", query = "SELECT p FROM PlatformUser p WHERE p.username = :username"),
    @NamedQuery(name = "PlatformUser.findByAccessGroup116", query = "SELECT p FROM PlatformUser p WHERE p.accessGroup116 = :accessGroup116")})
public class PlatformUser implements Serializable {
    private static final long serialVersionUID = 1L; 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "username", nullable = false, length = 2147483647)
    private String username;
    @Size(max = 2147483647)
    @Column(name = "access_group_116", length = 2147483647)
    private String accessGroup116;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator", fetch = FetchType.EAGER)
    private List<ComplaintHistory> complaintHistoryList;
    @OneToMany(mappedBy = "assignee", fetch = FetchType.EAGER)
    private List<JobHistory> jobHistoryList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator", fetch = FetchType.EAGER)
    private List<JobHistory> jobHistoryList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator", fetch = FetchType.EAGER)
    private List<Job> jobList;
    @OneToMany(mappedBy = "assignee", fetch = FetchType.EAGER)
    private List<Job> jobList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator", fetch = FetchType.EAGER)
    private List<Complaint> complaintList;

    public PlatformUser() {
    }

    public PlatformUser(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccessGroup116() {
        return accessGroup116;
    }

    public void setAccessGroup116(String accessGroup116) {
        this.accessGroup116 = accessGroup116;
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
    public List<JobHistory> getJobHistoryList1() {
        return jobHistoryList1;
    }

    public void setJobHistoryList1(List<JobHistory> jobHistoryList1) {
        this.jobHistoryList1 = jobHistoryList1;
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
    public List<Job> getJobList1() {
        return jobList1;
    }

    public void setJobList1(List<Job> jobList1) {
        this.jobList1 = jobList1;
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
        if (!(object instanceof PlatformUser)) {
            return false;
        }
        PlatformUser other = (PlatformUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return username;
    }
    
}
