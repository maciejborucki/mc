/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.mi.mcloud.selfcare.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author bor
 */
@Entity
@Table(name = "platform_component", catalog = "mcloud", schema = "mcloud")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlatformComponent.findAll", query = "SELECT p FROM PlatformComponent p"),
    @NamedQuery(name = "PlatformComponent.findById", query = "SELECT p FROM PlatformComponent p WHERE p.id = :id")})
public class PlatformComponent implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @OneToMany(mappedBy = "component", fetch = FetchType.EAGER)
    private List<JobHistory> jobHistoryList;
    @OneToMany(mappedBy = "component", fetch = FetchType.EAGER)
    private List<Job> jobList;

    public PlatformComponent() {
    }

    public PlatformComponent(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlatformComponent)) {
            return false;
        }
        PlatformComponent other = (PlatformComponent) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.mi.mcloud.selfcare.entity.PlatformComponent[ id=" + id + " ]";
    }
    
}
