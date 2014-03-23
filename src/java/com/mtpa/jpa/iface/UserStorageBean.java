//140314    MtpA    Created

package com.mtpa.jpa.iface;

import com.mtpa.jpa.ejb.UserStorageLocal;
import com.mtpa.jpa.entity.ENTUser;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Local(UserStorageLocal.class)
public class UserStorageBean implements Serializable, UserStorageLocal {

    @PersistenceContext
    EntityManager userEM;
    
    public UserStorageBean() {
        
    }

    @Override
    public List<ENTUser> getUserDetails() {
        return userEM.createNamedQuery("findAllUsers").getResultList();
    }
    
    @Override
    public void setUserDetails(String vForename, String vSurname) {
        ENTUser user = new ENTUser(vForename, vSurname);
        userEM.persist(user);
    }
    
    @PostConstruct
    public void postConstruct() {
        System.out.println("PersonStorageBean - on the way in");        
    }
    
    @PreDestroy
    public void preDestroy() {
        System.out.println("PersonStorageBean - on the way out");
    }
}
