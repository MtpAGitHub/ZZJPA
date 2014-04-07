/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mtpa.jpa.jsf;

/**
 *
 * @author MtpA
 * 060414   Add new arg to registerUser to utilise enum and make safe allocation as a USER (always from this screen)
 * 230314   Created bean
 */
import com.mtpa.jpa.enums.UserGroupEnum;
import com.mtpa.jpa.iface.DateStampLocal;
import com.mtpa.jpa.iface.UserJPALocal;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("register")
@RequestScoped
public class JSFRegisterBean {

     //uses backing beans in the business container (to do the business logic)
    @EJB
    UserJPALocal registeredUser;
    @EJB
    DateStampLocal registerDate;
    
     //uses JSF backing beans from the JSF container
    @Inject
    JSFDebugBean debugMsg;
    
    private String  userForename;
    private String  userSurname;
    private String  userUsername;
    private String  userPassword;
    private String  confPassword;
    private UserGroupEnum userGroup = UserGroupEnum.USER;

    public JSFRegisterBean() {
    }

    //standard getters & setters
    public String getUserForename() {
        return userForename;
    }

    public void setUserForename(String userForename) {
        this.userForename = userForename;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getConfPassword() {
        return confPassword;
    }

    public void setConfPassword(String confPassword) {
        this.confPassword = confPassword;
    }

    public UserGroupEnum getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroupEnum userGroup) {
        this.userGroup = userGroup;
    }

    //register the new user and utilise the ENUM to make sure the role cannot be invalid (default is USER)
    public String registerUser() {
        registeredUser.setUserDetails(userForename, userSurname, userUsername, userPassword, userGroup, registerDate.getWsDateStamp());
        debugMsg.setDebugText("We have registered");
        return "index";
    }
}
