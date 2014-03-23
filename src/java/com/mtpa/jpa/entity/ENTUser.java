//130314    MtpA    Create

package com.mtpa.jpa.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

@Entity
@NamedQuery(name="findAllUsers", query = "SELECT c FROM ENTUser c")
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
    
    public ENTUser() {
        
    }
    
    public ENTUser(String cForename, String cSurname) {
        this.forename = cForename;
        this.surname = cSurname;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.personId);
        hash = 47 * hash + Objects.hashCode(this.forename);
        hash = 47 * hash + Objects.hashCode(this.surname);
        hash = 47 * hash + Objects.hashCode(this.username);
        hash = 47 * hash + Objects.hashCode(this.password);
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
        return true;
    }

    @Override
    public String toString() {
        return "ENTUser{" + "personId=" + personId + ", forename=" + forename + ", surname=" + surname + ", username=" + username + ", password=" + password + '}';
    }

}
