/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mtpa.jpa.jsf;

import com.mtpa.jpa.enums.UserGroupEnum;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author MtpA
 * 260314   Created to display the available currency values on the screen through a drop down list
 */
@Named("usergroup")
@RequestScoped
public class JSFUserGroupBean {

    public JSFUserGroupBean() {
        
    }
    
    // create an array of the enum values which will be rendered as the selectOneMenu items
    public UserGroupEnum[] getUserGroup() {
        return UserGroupEnum.values();
    }
}
