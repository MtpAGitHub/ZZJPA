/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mtpa.jpa.iface;

import com.mtpa.jpa.entity.ENTUser;
import java.util.Date;
import java.util.List;

/**
 *
 * @author MtpA
 */
public interface UserJPALocal {

    List<ENTUser> getAllUsers();
    
    List<ENTUser> getAllTpUsers(long vUserId);
    
    ENTUser getUserByName(String vUsername);
    
    ENTUser getUserById(long vUserId);
    
    ENTUser getUserByIDFetch(long vUserId);
    
    ENTUser getUserByNameFetch(String vUsername);
    
    boolean userExist(String vUsername);

    void setUserDetails(String vForename, String vSurname, String vUsername, String vPassword, Date vCreatedDate);
    
}
