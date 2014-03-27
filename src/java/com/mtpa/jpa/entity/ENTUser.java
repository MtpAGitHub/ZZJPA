//130314    MtpA    Create

package com.mtpa.jpa.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

@Entity
@NamedQueries({
    @NamedQuery(name="findAllUsers", query = "SELECT user FROM ENTUser user"),
    @NamedQuery(name="findSingleUser", query = "SELECT user FROM ENTUser user WHERE user.username = :username")
})
public class ENTUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personId;
    
    @NotNull
    private String forename;
    
    @NotNull
    private String surname;
    
    @NotNull
    private String username;
    
    @NotNull
    private String password;
    
    @NotNull
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date createdDate;
    
    public ENTUser() {
        
    }
    
    public ENTUser(String cForename, String cSurname, String cUsername, String cPassword, Date cCreatedDate) {
        this.forename = cForename;
        this.surname = cSurname;
        this.username = cUsername;
        this.password = cPassword;
        this.createdDate =cCreatedDate;
    }
    
    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long id) {
        this.personId = id;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.personId);
        hash = 71 * hash + Objects.hashCode(this.forename);
        hash = 71 * hash + Objects.hashCode(this.surname);
        hash = 71 * hash + Objects.hashCode(this.username);
        hash = 71 * hash + Objects.hashCode(this.password);
        hash = 71 * hash + Objects.hashCode(this.createdDate);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ENTUser other = (ENTUser) obj;
        if (!Objects.equals(this.personId, other.personId)) {
            return false;
        }
        if (!Objects.equals(this.forename, other.forename)) {
            return false;
        }
        if (!Objects.equals(this.surname, other.surname)) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.createdDate, other.createdDate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTUser{" + "personId=" + personId + ", forename=" + forename + ", surname=" + surname + ", username=" + username + ", password=" + password + ", createdDate=" + createdDate + '}';
    }

}
