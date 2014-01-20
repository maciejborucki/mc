/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.mi.mcloud.selfcare.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bor
 */
@Entity
@Table(name = "access_group", catalog = "mcloud", schema = "mcloud")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccessGroup.findAll", query = "SELECT a FROM AccessGroup a"),
    @NamedQuery(name = "AccessGroup.findById", query = "SELECT a FROM AccessGroup a WHERE a.id = :id"),
    @NamedQuery(name = "AccessGroup.findByGroupName", query = "SELECT a FROM AccessGroup a WHERE a.groupName = :groupName"),
    @NamedQuery(name = "AccessGroup.findByAccessResources", query = "SELECT a FROM AccessGroup a WHERE a.accessResources = :accessResources"),
    @NamedQuery(name = "AccessGroup.findByAccessType", query = "SELECT a FROM AccessGroup a WHERE a.accessType = :accessType")})
public class AccessGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "group_name", nullable = false, length = 2147483647)
    private String groupName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "access_resources", nullable = false, length = 2147483647)
    private String accessResources;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "access_type", nullable = false, length = 2147483647)
    private String accessType;

    public AccessGroup() {
    }

    public AccessGroup(Long id) {
        this.id = id;
    }

    public AccessGroup(Long id, String groupName, String accessResources, String accessType) {
        this.id = id;
        this.groupName = groupName;
        this.accessResources = accessResources;
        this.accessType = accessType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getAccessResources() {
        return accessResources;
    }

    public void setAccessResources(String accessResources) {
        this.accessResources = accessResources;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
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
        if (!(object instanceof AccessGroup)) {
            return false;
        }
        AccessGroup other = (AccessGroup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.mi.mcloud.selfcare.entity.AccessGroup[ id=" + id + " ]";
    }
    
}
