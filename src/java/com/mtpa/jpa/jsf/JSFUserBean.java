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
 *          Added workingUserId & workingUsername so that can show user home page (with user specific data) and admin page (with a picked user)
 *          Added all of the getAdmin...() methods to can differentiate the above
 *          Added the usergroup stuff to allow an admin user to add another admin user
 * 040414   Added list query for all requests made of me & all transactions relating to my accounts
 * 030414   Add new list query using FETCH JOIN for newly added annotations & remove redundant code
 * 120314   Created
 */
import com.mtpa.jpa.entity.ENTAccount;
import com.mtpa.jpa.entity.ENTRequest;
import com.mtpa.jpa.entity.ENTTransaction;
import com.mtpa.jpa.iface.UserJPALocal;
import com.mtpa.jpa.entity.ENTUser;
import com.mtpa.jpa.enums.AllUserEnum;
import com.mtpa.jpa.enums.UserGroupEnum;
import com.mtpa.jpa.iface.AccountJPALocal;
import com.mtpa.jpa.iface.GetTPUserLocal;
import com.mtpa.jpa.iface.RequestJPALocal;
import com.mtpa.jpa.iface.TransactionJPALocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

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
    @EJB
    GetTPUserLocal tpUserList;

    private long userId;
    private long workingUserId;
    private String username;
    private String workingUsername;
    private String userForename;
    private String userSurname;
    private String userPassword;
    private String confirmPassword;
    private UserGroupEnum userGroup;
    private boolean allUser;

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

    public String getWorkingUsername() {
        return workingUsername;
    }

    public void setWorkingUsername(String workingUsername) {
        this.workingUsername = workingUsername;
    }

    public boolean isAllUser() {
        return allUser;
    }

    public void setAllUser(boolean allUser) {
        this.allUser = allUser;
    }

    public UserGroupEnum getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroupEnum userGroup) {
        this.userGroup = userGroup;
    }

    public String loginUser() {
        //creates an instance (context) holding the state information of the JSF request
        //creates an instance (request) which links the JSF context to a Servlet by which the realm information can be accessed
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            //checks against the realm in the Servlet (palpayJDBCRealm) for the users login credentials
            request.login(this.username, this.userPassword);
            ENTUser validUser = userDet.getUserByName(username);
            if (validUser != null) {
                this.userId = validUser.getPersonId();
                this.workingUserId = this.userId;
                this.userForename = validUser.getForename();
                this.userSurname = validUser.getSurname();
                //checks to see what role the user has, against the predefined roles in the enum.  Note it is if-else if-else for safety
                if (request.isUserInRole(UserGroupEnum.ADMIN.getUserGroup())) {
                    this.workingUsername = AllUserEnum.ALLUSER.getAllUsers();
                    return "admin";
                } else if (request.isUserInRole(UserGroupEnum.USER.getUserGroup())){
                    this.workingUsername = this.username;
                    return "home";
                } else {
                    return "loginerr";
                }
            } else {
                return "loginerr";
            }
        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage("Login failed."));
            return "loginerr";
        }
    }

    public String logoutUser() {
        //as per login it binds the JSF context to a Servlet allowing the user to be removed from the session
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            //this method will disassociate the principal from the session (effectively logging him/her out)
            request.logout();
            return "logout";
        } catch (ServletException e) {
            return "logouterr";
        }
    }
    
    //these methods get the user lists for the main home pages for users and admins
    //all user in the database regardless of which user
    @RolesAllowed("admins")
    public List<ENTUser> getAllUser() {
        return userDet.getAllUsers();
    }

    //all users with a FETCH JOIN (not yet implemented)
    public ENTUser getUserFetchByName() {
        return userDet.getUserByNameFetch(username);
    }

    //these methods get the account lists for the main home pages for users and admins
    //all user in the database regardless of which user
    @RolesAllowed("admins")
    public List<ENTAccount> getAllAccounts() {
        return accountDet.getAccountList();
    }

    //get the accounts that belong to the logged in user
    public List<ENTAccount> getAccountsByUserId() {
        return accountDet.getUserAccountList(workingUserId);
    }
    
    //used on the admin home page only.  If 'All users' selected then get all accounts otherwise get the accounts of the selected user
    public List<ENTAccount> getAdminAccounts() {
        if(workingUsername.equals(AllUserEnum.ALLUSER.getAllUsers())) {
            return accountDet.getAccountList();
        } else {
            ENTUser workingUser = userDet.getUserByName(workingUsername);
            workingUserId = workingUser.getPersonId();
            return accountDet.getUserAccountList(workingUserId);            
        }
    }
    
    //these methods get the request lists for the main home pages for users and admins
    //all requests in the database regardless of which user
    @RolesAllowed("admins")
    public List<ENTRequest> getAllRequests() {
        return requestDet.getRequestList();
    }

    //get requests made of me by other users
    public List<ENTRequest> getRequesteeRequests() {
        return requestDet.getRequesteeList(workingUserId);
    }

    //get requests made by me of other users (not yet implemented)
    public List<ENTRequest> getRequestorRequests() {
        return requestDet.getRequestorList(workingUserId);
    }

    //used on the admin home page only.  If 'All users' selected then get all requests otherwise get the requests of the selected user
    public List<ENTRequest> getAdminRequests() {
        if(workingUsername.equals(AllUserEnum.ALLUSER.getAllUsers())) {
            return requestDet.getRequestList();
        } else {
            ENTUser workingUser = userDet.getUserByName(workingUsername);
            workingUserId = workingUser.getPersonId();
            return requestDet.getRequesteeList(workingUserId);
        }        
    }
    
    //these methods get the transaction lists for the main home pages for users and admins
    //all transactions in the database regardless of which account
    @RolesAllowed("admins")
    public List<ENTTransaction> getAllTransactions() {
        return transDet.getTransactionList();
    }

    //only transactions that belong to accounts that belong to the logged in user
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

    //used on the admin home page only.  If 'All users' selected then get all transactions otherwise get the transactions of the selected user
    public List<ENTTransaction> getAdminTrans() {
        if(workingUsername.equals(AllUserEnum.ALLUSER.getAllUsers())) {
            return transDet.getTransactionList();
        } else {
            ENTUser workingUser = userDet.getUserByName(workingUsername);
            workingUserId = workingUser.getPersonId();
            return getMyTransactions();
        }                
    }

    //used on the admin home page to list all users that are not the logged in user and adds 'All users' as the top entry on drop down list
    public List<String> getTpUsers() {
        List<String> allTPUsers = tpUserList.getTPUserList(workingUserId);
        allTPUsers.add(0, AllUserEnum.ALLUSER.getAllUsers());
        return allTPUsers;
    }

    //listens on the adminHome page for a change of user.  If 'All Users' selected from enum set the boolean
    public void userChangeListener(AjaxBehaviorEvent usernameEvent) {
        if(workingUsername.equals(AllUserEnum.ALLUSER.getAllUsers())) {
            setAllUser(true);
        } else {
            setAllUser(false);
        }
    }

}
