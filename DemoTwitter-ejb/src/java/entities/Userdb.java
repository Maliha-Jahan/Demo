/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "USERDB")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Userdb.findAll", query = "SELECT u FROM Userdb u"),
    @NamedQuery(name = "Userdb.findByEmail", query = "SELECT u FROM Userdb u WHERE u.email = :email"),
    @NamedQuery(name = "Userdb.findByPassword", query = "SELECT u FROM Userdb u WHERE u.password = :password"),
    @NamedQuery(name = "Userdb.findByDisplayName", query = "SELECT u FROM Userdb u WHERE u.displayName = :displayName"),
    @NamedQuery(name = "Userdb.findByAge", query = "SELECT u FROM Userdb u WHERE u.age = :age"),
    @NamedQuery(name = "Userdb.login", query = "SELECT l FROM Userdb l WHERE l.email = :email and l.password = :password"),
    @NamedQuery(name = "Userdb.search", query = "SELECT l FROM Userdb l WHERE l.email like :key or l.displayName = :key")
})
public class Userdb implements Serializable {
    @ManyToMany(mappedBy = "userdbCollection")
    private Collection<Postdb> postdbCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "email")
    private Collection<Postdb> postdbCollection1;
    private static final long serialVersionUID = 1L;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "EMAIL")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "PASSWORD")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "DISPLAY_NAME")
    private String displayName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AGE")
    private BigInteger age;
    @JoinTable(name = "FOLLOWDB", joinColumns = {
        @JoinColumn(name = "EMAIL", referencedColumnName = "EMAIL")}, inverseJoinColumns = {
        @JoinColumn(name = "FOLLOWS_EMAIL", referencedColumnName = "EMAIL")})
    @ManyToMany
    private Collection<Userdb> userdbCollection;
    @ManyToMany(mappedBy = "userdbCollection")
    private Collection<Userdb> userdbCollection1;

    public Userdb() {
    }

    public Userdb(String email) {
        this.email = email;
    }

    public Userdb(String email, String password, String displayName, BigInteger age) {
        this.email = email;
        this.password = password;
        this.displayName = displayName;
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public BigInteger getAge() {
        return age;
    }

    public void setAge(BigInteger age) {
        this.age = age;
    }

    @XmlTransient
    public Collection<Userdb> getUserdbCollection() {
        return userdbCollection;
    }

    public void setUserdbCollection(Collection<Userdb> userdbCollection) {
        this.userdbCollection = userdbCollection;
    }

    @XmlTransient
    public Collection<Userdb> getUserdbCollection1() {
        return userdbCollection1;
    }

    public void setUserdbCollection1(Collection<Userdb> userdbCollection1) {
        this.userdbCollection1 = userdbCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (email != null ? email.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Userdb)) {
            return false;
        }
        Userdb other = (Userdb) object;
        if ((this.email == null && other.email != null) || (this.email != null && !this.email.equals(other.email))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Userdb[ email=" + email + " ]";
    }

    @XmlTransient
    public Collection<Postdb> getPostdbCollection() {
        return postdbCollection;
    }

    public void setPostdbCollection(Collection<Postdb> postdbCollection) {
        this.postdbCollection = postdbCollection;
    }

    @XmlTransient
    public Collection<Postdb> getPostdbCollection1() {
        return postdbCollection1;
    }

    public void setPostdbCollection1(Collection<Postdb> postdbCollection1) {
        this.postdbCollection1 = postdbCollection1;
    }
    
}
