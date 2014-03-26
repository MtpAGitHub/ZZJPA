//260314    MtpA    Added password encryption from lab example
//140314    MtpA    Created

package com.mtpa.jpa.ejb;

import com.mtpa.jpa.entity.ENTUser;
import com.mtpa.jpa.entity.ENTUserGroup;
import com.mtpa.jpa.iface.UserStorageLocal;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public void setUserDetails(String vForename, String vSurname, String vUsername, String vPassword, Date vCreatedDate) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String passwd = vPassword;
            md.update(passwd.getBytes("UTF-8")); // Change this to "UTF-16" if needed
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String paswdToStoreInDB = bigInt.toString(16);

            ENTUser user = new ENTUser(vForename, vSurname, vUsername, vPassword, vCreatedDate);
            userEM.persist(user);
            
            ENTUserGroup userGroup = new ENTUserGroup(vUsername, "users");
            userEM.persist(userGroup);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(UserStorageBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @PostConstruct
    public void postConstruct() {
        System.out.println("UserStorageBean - on the way in");        
    }
    
    @PreDestroy
    public void preDestroy() {
        System.out.println("UserStorageBean - on the way out");
    }
}
