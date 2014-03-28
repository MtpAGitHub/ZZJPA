//120314    MtpA    Created
package com.mtpa.jpa.jsf;

import com.mtpa.jpa.iface.UserJPALocal;
import com.mtpa.jpa.entity.ENTUser;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("user")
@SessionScoped
public class JSFUserBean implements Serializable {

    @Inject
    JSFDebugBean debugMsg;

    @EJB
    UserJPALocal userDet;

    private long userId;
    private String username;
    private String userForename;
    private String userSurname;
    private String userPassword;
    private String confirmPassword;

    public JSFUserBean() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
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

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String loginUser() {
        /*        FacesContext context = FacesContext.getCurrentInstance();
         HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
         try {
         //this method will actually check in the realm for the provided credentials
         request.login(this.username, this.password);
         } catch (ServletException e) {
         context.addMessage(null, new FacesMessage("Login failed."));
         return "loginerr";
         }
         */
        if (!this.username.equals("error")) {
            ENTUser validUser = userDet.getUser(username);
            this.userId = validUser.getPersonId();
            this.userForename = validUser.getForename();
            this.userSurname = validUser.getSurname();
            return "home";
        } else {
            return "loginerr";
        }
    }

    public String logoutUser() {
        /*        FacesContext context = FacesContext.getCurrentInstance();
         HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
         try {
         //this method will disassociate the principal from the session (effectively logging him/her out)
         request.logout();
         return "logout";
         } catch (ServletException e) {
         return "logouterr";
         }
         */
        return "logout";
    }

    public String submitUser() {
        debugMsg.setDebugText("User submitted");
        return "results";
    }

    public List<ENTUser> getAllUser() {
        return userDet.getAllUsers();
    }

    public String newAccount() {
        debugMsg.setDebugText("New account");
        return "account";
    }

    public String newRequest() {
        debugMsg.setDebugText("New request");
        return "request";
    }

    public String newPayment() {
        debugMsg.setDebugText("New payment");
        return "payment";
    }
}
