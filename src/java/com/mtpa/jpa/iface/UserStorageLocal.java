/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mtpa.jpa.iface;

import com.mtpa.jpa.entity.ENTUser;
import java.util.List;

/**
 *
 * @author MtpA
 */
public interface UserStorageLocal {

    List<ENTUser> getUserDetails();

    void setUserDetails(String vForename, String vSurname, String vUsername, String vPassword);
    
}
