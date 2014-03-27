//230314    MtpA    Created

package com.mtpa.jpa.jsf;

import com.mtpa.jpa.enums.CurrencyEnum;
import com.mtpa.jpa.iface.DateStampLocal;
import com.mtpa.jpa.iface.UserJPALocal;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("register")
@RequestScoped
public class JSFRegisterBean {

    @EJB
    UserJPALocal registeredUser;
    @EJB
    DateStampLocal registerDate;
    
    @Inject
    JSFDebugBean debugMsg;
    
    private String  userForename;
    private String  userSurname;
    private String  userUsername;
    private String  userPassword;
    private String  confPassword;

    public JSFRegisterBean() {
    }

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

    public String registerUser() {
        registeredUser.setUserDetails(userForename, userSurname, userUsername, userPassword, registerDate.getWsDateStamp());
        debugMsg.setDebugText("We have registered");
        return "home";
    }
}
