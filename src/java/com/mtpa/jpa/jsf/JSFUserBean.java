/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtpa.jpa.jsf;

/**
 *
 * @author MtpA
 * 060414   Added RolesAllowed annotations to restrict access to admin only methods
 *          Added workingUserId so that can show user home page (with user specific data) and admin page (with a picked user)
 * 040414   Added list query for all requests made of me & all transactions relating to my accounts
 * 030414   Add new list query using FETCH JOIN for newly added annotations & remove redundant code
 * 120314   Created
 */
import com.mtpa.jpa.entity.ENTAccount;
import com.mtpa.jpa.entity.ENTRequest;
import com.mtpa.jpa.entity.ENTTransaction;
import com.mtpa.jpa.iface.UserJPALocal;
import com.mtpa.jpa.entity.ENTUser;
import com.mtpa.jpa.iface.AccountJPALocal;
import com.mtpa.jpa.iface.RequestJPALocal;
import com.mtpa.jpa.iface.TransactionJPALocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
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
    @EJB
    AccountJPALocal accountDet;
    @EJB
    TransactionJPALocal transDet;
    @EJB
    RequestJPALocal requestDet;

    private long userId;
    private long workingUserId;
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

    public long getWorkingUserId() {
        return workingUserId;
    }

    public void setWorkingUserId(long workingUserId) {
        this.workingUserId = workingUserId;
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
            ENTUser validUser = userDet.getUserByName(username);
            if (validUser != null) {
                this.userId = validUser.getPersonId();
                this.workingUserId = this.userId;
                this.userForename = validUser.getForename();
                this.userSurname = validUser.getSurname();
                if (username.equals("admin")) {
                    return "admin";
                } else {
                    return "home";
                }
            } else {
                return "loginerr";
            }
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
    
    public List<ENTUser> getAllUser() {
        return userDet.getAllUsers();
    }

    public ENTUser getUserFetchByName() {
        return userDet.getUserByNameFetch(username);
    }

//    @RolesAllowed("admin")
    public List<ENTAccount> getAllAccounts() {
        return accountDet.getAccountList();
    }

    public List<ENTAccount> getAccountsByUserId() {
        return accountDet.getUserAccountList(workingUserId);
    }
    
//    @RolesAllowed("admin")
    public List<ENTRequest> getAllRequests() {
        return requestDet.getRequestList();
    }

    public List<ENTRequest> getRequesteeRequests() {
        return requestDet.getRequesteeList(workingUserId);
    }

    public List<ENTRequest> getRequestorRequests() {
        return requestDet.getRequestorList(workingUserId);
    }

//    @RolesAllowed("admin")
    public List<ENTTransaction> getAllTransactions() {
        return transDet.getTransactionList();
    }
    
    public List<ENTTransaction> getMyTransactions() {
        List<ENTAccount> myAccounts = getAccountsByUserId();
        if (myAccounts.size() > 0) {
            List<Long> myAcctIds = new ArrayList<>();
            for (ENTAccount curAcct : myAccounts) {
                myAcctIds.add(curAcct.getId());
            }
            return transDet.getTransByAcctId(myAcctIds);
        } else {
            List<ENTTransaction> emptyList = new ArrayList<>();
            return emptyList;
        }
    }
    
}
