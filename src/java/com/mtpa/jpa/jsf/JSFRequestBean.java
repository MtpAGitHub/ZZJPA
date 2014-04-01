//270314    MtpA    Created

package com.mtpa.jpa.jsf;

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

    @Inject
    JSFDebugBean debugTxt;
    @Inject
    JSFErrorBean errorTxt;
    @Inject
    JSFUserBean curUser;
    
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

    public List<String> getAllTpUsers() {
        return userList.getTPUserList(curUser.getUserId());
    }

    public List<String> getMyAccounts() {
        return acctList.myAccountList(curUser.getUserId());        
    }
    
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
