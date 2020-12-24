/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "POSTDB")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Postdb.findAll", query = "SELECT p FROM Postdb p"),
    @NamedQuery(name = "Postdb.findByPostId", query = "SELECT p FROM Postdb p WHERE p.postId = :postId"),
    @NamedQuery(name = "Postdb.findByPostTime", query = "SELECT p FROM Postdb p WHERE p.postTime = :postTime"),
    @NamedQuery(name = "Postdb.findByPostBody", query = "SELECT p FROM Postdb p WHERE p.postBody = :postBody"),
    @NamedQuery(name = "Postdb.findByEmail", query = "SELECT p FROM Postdb p WHERE p.email = :email")})
public class Postdb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "POST_ID")
    private String postId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "POST_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date postTime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "POST_BODY")
    private String postBody;
    @JoinTable(name = "LIKESDB", joinColumns = {
        @JoinColumn(name = "POST_ID", referencedColumnName = "POST_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "EMAIL", referencedColumnName = "EMAIL")})
    @ManyToMany
    private Collection<Userdb> userdbCollection;
    @JoinColumn(name = "EMAIL", referencedColumnName = "EMAIL")
    @ManyToOne(optional = false)
    private Userdb email;

    public Postdb() {
    }

    public Postdb(String postId) {
        this.postId = postId;
    }

    public Postdb(String postId, Date postTime, String postBody) {
        this.postId = postId;
        this.postTime = postTime;
        this.postBody = postBody;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    @XmlTransient
    public Collection<Userdb> getUserdbCollection() {
        return userdbCollection;
    }

    public void setUserdbCollection(Collection<Userdb> userdbCollection) {
        this.userdbCollection = userdbCollection;
    }

    public Userdb getEmail() {
        return email;
    }

    public void setEmail(Userdb email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (postId != null ? postId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Postdb)) {
            return false;
        }
        Postdb other = (Postdb) object;
        if ((this.postId == null && other.postId != null) || (this.postId != null && !this.postId.equals(other.postId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Postdb[ postId=" + postId + " ]";
    }
    
}
