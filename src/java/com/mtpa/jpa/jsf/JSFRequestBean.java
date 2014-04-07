/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mtpa.jpa.jsf;

 /**
 *
 * @author MtpA
 * 270314   Created to make requests for payments from other accounts
 *
 */

import com.mtpa.jpa.entity.ENTAccount;
import com.mtpa.jpa.entity.ENTUser;
import com.mtpa.jpa.iface.AccountJPALocal;
import com.mtpa.jpa.iface.DateStampLocal;
import com.mtpa.jpa.iface.GetAccountListLocal;
import com.mtpa.jpa.iface.GetTPUserLocal;
import com.mtpa.jpa.iface.RequestJPALocal;
import com.mtpa.jpa.iface.UserJPALocal;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("newrequest")
@RequestScoped
public class JSFRequestBean {

     //uses JSF backing beans from the JSF container
    @Inject
    JSFDebugBean debugTxt;
    @Inject
    JSFErrorBean errorTxt;
    @Inject
    JSFUserBean curUser;
    
     //uses backing beans in the business container (to do the business logic)
    @EJB
    RequestJPALocal userRequest;
    @EJB
    GetTPUserLocal userList;
    @EJB
    UserJPALocal tpUser;
    @EJB
    DateStampLocal dateStamp;
    @EJB
    GetAccountListLocal acctList;
    @EJB
    AccountJPALocal requestorAccount;
    
    private String requestUser;
    private String myAcctName;
    private double requestAmt;

    public JSFRequestBean() {
        
    }

    //standard getters & setters
    public String getRequestUser() {
        return requestUser;
    }

    public void setRequestUser(String requestUser) {
        this.requestUser = requestUser;
    }

    public double getRequestAmt() {
        return requestAmt;
    }

    public void setRequestAmt(double requestAmt) {
        this.requestAmt = requestAmt;
    }

    public String getMyAcctName() {
        return myAcctName;
    }

    public void setMyAcctName(String myAcctName) {
        this.myAcctName = myAcctName;
    }

    //get all third party users to show on the screen drop down list
    public List<String> getAllTpUsers() {
        return userList.getTPUserList(curUser.getUserId());
    }

    //get all third party user accounts to show on the screen drop down list
    public List<String> getMyAccounts() {
        return acctList.myAccountList(curUser.getUserId());        
    }
    
    //get the requested user and instantiate an object
    //get details of my account which I have selected them to pay into
    //add the request to the table saying it is from me, for an amount, in my currency, to my account and to whom I want the payment from
    //lastly there is the web service call to timestamp the request
    public String submitRequest() {
        ENTUser requesteeUser = tpUser.getUserByName(requestUser);
        if (requesteeUser != null) {
            ENTAccount myAcct = requestorAccount.getSingleAccount(myAcctName);
            userRequest.createRequest(curUser.getUserId(), requesteeUser.getPersonId(), requestAmt, myAcct.getId(), myAcct.getAcctCurrency(),dateStamp.getWsDateStamp());
        } else {
        }
        debugTxt.setDebugText("Requested " + requestAmt + " from " + requestUser);
        return "home";
    }
}
