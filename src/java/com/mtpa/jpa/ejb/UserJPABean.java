//260314    MtpA    Added password encryption from lab example
//140314    MtpA    Created

package com.mtpa.jpa.ejb;

import com.mtpa.jpa.entity.ENTUser;
import com.mtpa.jpa.entity.ENTUserGroup;
import com.mtpa.jpa.iface.UserJPALocal;
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
import javax.persistence.Query;

@Stateless
@Local(UserJPALocal.class)
public class UserJPABean implements Serializable, UserJPALocal {

    @PersistenceContext
    EntityManager userEM;
    
    public UserJPABean() {
        
    }

    @Override
    public synchronized List<ENTUser> getAllUsers() {
        return userEM.createNamedQuery("findAllUsers").getResultList();
    }
    
    @Override
    public synchronized ENTUser getUser(String vUsername) {
        
        //Used getResultList as opposed to getSingleResult (exception handling as can't guarantee only one record)
        //Did not use find() method as username is not the PK
        
        Query findSingleUser = userEM.createNamedQuery("findSingleUser");
        findSingleUser.setParameter("username", vUsername);
        List<ENTUser> userDetail = findSingleUser.getResultList();
        if (!userDetail.isEmpty()) {
            return userDetail.get(0);
        } else {
            return null;
        }
    }

    @Override
    public synchronized boolean userExist(String vUsername) {
        
        //Used getResultList as opposed to getSingleResult (exception handling as can't guarantee only one record)
        //Did not use find() method as username is not the PK

        Query findSingleUser = userEM.createNamedQuery("findSingleUser");
        findSingleUser.setParameter("username", vUsername);
        List<ENTUser> userDetail = findSingleUser.getResultList();
        if (!userDetail.isEmpty()) {
            return true;
        } else {
            return false;
        }        
    }
    
    @Override
    public synchronized void setUserDetails(String vForename, String vSurname, String vUsername, String vPassword, Date vCreatedDate) {
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
            Logger.getLogger(UserJPABean.class.getName()).log(Level.SEVERE, null, ex);
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
