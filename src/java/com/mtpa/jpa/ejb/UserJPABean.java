/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtpa.jpa.ejb;

/**
 *
 * @author MtpA
 * 060414   Added enum UserRole to args 
 * 260314   Added password encryption from lab example
 * 140314   Created bean
 */
import com.mtpa.jpa.entity.ENTUser;
import com.mtpa.jpa.entity.ENTUserGroup;
import com.mtpa.jpa.enums.UserGroupEnum;
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
    public synchronized List<ENTUser> getAllTpUsers(long vUserId) {
        Query findAllTpUsers = userEM.createNamedQuery("findAllTpUsers");
        findAllTpUsers.setParameter("userid", vUserId);
        return findAllTpUsers.getResultList();
    }

    @Override
    public synchronized ENTUser getUserByName(String vUsername) {
        
        //Used getResultList as opposed to getSingleResult (exception handling as can't guarantee only one record)
        //Did not use find() method as username is not the PK
        
        Query findSingleUser = userEM.createNamedQuery("findUserByName");
        findSingleUser.setParameter("username", vUsername);
        List<ENTUser> userDetail = findSingleUser.getResultList();
        if (!userDetail.isEmpty()) {
            return userDetail.get(0);
        } else {
            return null;
        }
    }

    @Override
    public synchronized ENTUser getUserById(long vUserId) {
        
        //Used getResultList as opposed to getSingleResult (exception handling as can't guarantee only one record)
        //Did not use find() method as username is not the PK
        
        Query findSingleUser = userEM.createNamedQuery("findUserById");
        findSingleUser.setParameter("userid", vUserId);
        List<ENTUser> userDetail = findSingleUser.getResultList();
        if (!userDetail.isEmpty()) {
            return userDetail.get(0);
        } else {
            return null;
        }
    }
    
    @Override
    public synchronized ENTUser getUserByIDFetch(long vUserId) {
        //Used getResultList as opposed to getSingleResult (exception handling as can't guarantee only one record)
        //Did not use find() method as wanted JOIN FETCH

        Query idFetch = userEM.createNamedQuery("findUserIdFetch");
        idFetch.setParameter("userid", vUserId);
        List<ENTUser> userDetail = idFetch.getResultList();
        if (!userDetail.isEmpty()) {
            return userDetail.get(0);
        } else {
            return null;
        }
    }

    @Override
    public synchronized ENTUser getUserByNameFetch(String vUsername) {
        //Used getResultList as opposed to getSingleResult (exception handling as can't guarantee only one record)
        //Did not use find() method as username is not the PK

        Query nameFetch = userEM.createNamedQuery("findUsernameFetch");
        nameFetch.setParameter("username", vUsername);
        List<ENTUser> userDetail = nameFetch.getResultList();
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
    public synchronized void setUserDetails(String vForename, String vSurname, String vUsername, String vPassword, UserGroupEnum vUserGroup, Date vCreatedDate) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String passwd = vPassword;
            md.update(passwd.getBytes("UTF-8")); // Change this to "UTF-16" if needed
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String paswdToStoreInDB = bigInt.toString(16);

            ENTUser user = new ENTUser(vForename, vSurname, vUsername, vPassword, vCreatedDate);
            userEM.persist(user);
   System.out.println(">>" + vUserGroup.getUserGroup());
            ENTUserGroup userGroup = new ENTUserGroup(vUsername, vUserGroup.getUserGroup());
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
