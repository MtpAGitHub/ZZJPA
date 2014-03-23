//120314    MtpA    Created

package com.mtpa.jpa.jsf;

import com.mtpa.jpa.iface.UserStorageLocal;
import com.mtpa.jpa.entity.ENTUser;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("user")
@RequestScoped
public class JSFUserBean implements Serializable {

    @Inject
    JSFDebugBean debugMsg;
    
    @EJB
    UserStorageLocal userDet;
    
    private String forename;
    private String surname;

    public JSFUserBean() {
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
    
    public String submitUser() {
        debugMsg.setDebugText("User submitted");
        return "results";
    }

    public List<ENTUser> getAllUser() {
        return userDet.getUserDetails();
    }
}
