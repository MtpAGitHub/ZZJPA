//270314    MtpA    Created

package com.mtpa.jpa.jsf;

import com.mtpa.jpa.entity.ENTAccount;
import com.mtpa.jpa.entity.ENTUser;
import com.mtpa.jpa.iface.AccountJPALocal;
import com.mtpa.jpa.iface.AdjustBalanceLocal;
import com.mtpa.jpa.iface.DateStampLocal;
import com.mtpa.jpa.iface.TransactionJPALocal;
import com.mtpa.jpa.iface.UserJPALocal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("payment")
@RequestScoped
public class JSFPaymentBean {

    @Inject
    JSFDebugBean debugTxt;
    @Inject
    JSFErrorBean errorTxt;
    @Inject
    JSFUserBean curUser;

    @EJB
    UserJPALocal tpUser;
    @EJB
    AccountJPALocal userAcct;
    @EJB
    TransactionJPALocal paymentTrans;
    @EJB
    DateStampLocal createdDate;
    @EJB
    AdjustBalanceLocal changedBalance;
    
    private String paymentActName;
    private long paymentActId;
    private String payeeUsername;
    private long payeeUserId;
    private String payeeActName;
    private long payeeActId;
    private double paymentAmt;
    private List<String> accountNameList;
    
    public JSFPaymentBean() {
        
    }

    public String getPayeeUsername() {
        return payeeUsername;
    }

    public void setPayeeUsername(String payeeUsername) {
        this.payeeUsername = payeeUsername;
    }

    public long getPayeeActId() {
        return payeeActId;
    }

    public void setPayeeActId(long payeeActId) {
        this.payeeActId = payeeActId;
    }

    public double getPaymentAmt() {
        return paymentAmt;
    }

    public void setPaymentAmt(double paymentAmt) {
        this.paymentAmt = paymentAmt;
    }

    public String getPaymentActName() {
        return paymentActName;
    }

    public void setPaymentActName(String paymentActName) {
        this.paymentActName = paymentActName;
    }

    public long getPaymentActId() {
        return paymentActId;
    }

    public void setPaymentActId(long paymentActId) {
        this.paymentActId = paymentActId;
    }

    public long getPayeeUserId() {
        return payeeUserId;
    }

    public void setPayeeUserId(long payeeUserId) {
        this.payeeUserId = payeeUserId;
    }

    public String getPayeeActName() {
        return payeeActName;
    }

    public void setPayeeActName(String payeeActName) {
        this.payeeActName = payeeActName;
    }
    
    public List<String> getTpUsers() {
        List<ENTUser> userList = tpUser.getAllTpUsers(curUser.getUserId());
        List<String> usernameList = new ArrayList<>();
        if (userList.size() > 0) {
            for (ENTUser curUser : userList) {
                usernameList.add(curUser.getUsername());
            }            
        } else {
            usernameList.add("NoRecordsFound");
        }
        return usernameList;
    }
    
    public List<String> getTpAccounts() {
        ENTUser selectedUser = tpUser.getUser(payeeUsername);
        accountNameList = new ArrayList<>();
        if (selectedUser !=null) {
            getAccountList(selectedUser.getPersonId());
        } else {
            accountNameList.add("NoRecordsFound");
        }
        return accountNameList;
    }

    public List<String> getMyAccounts() {
        getAccountList(curUser.getUserId());
        return accountNameList;
    }
    
    public void getAccountList(long acctListUserId) {
        List<ENTAccount> acctList = userAcct.getUserAccountList(acctListUserId);
        if (acctList.size() > 0) {
            for (ENTAccount curAcct : acctList) {
                accountNameList.add(curAcct.getAccountName());
            }
        } else {
            accountNameList.add("NoRecordsFound");
        }        
    }

    public String submitPayment() {
        ENTAccount paymentAcct = userAcct.getSingleAccount(payeeActName);
        if (paymentAcct != null) {
            if (paymentAcct.getBalance() - paymentAmt > 0) {
                //take money out of my account
                paymentActId = paymentAcct.getId();
                double debitAmt = 0 - paymentAmt;
                paymentTrans.createTransaction(paymentActId, debitAmt, payeeUserId, payeeActId, createdDate.getWsDateStamp());
                changedBalance.adjustBalance(paymentActId, debitAmt);
                //pay money into their account
                paymentTrans.createTransaction(payeeActId, paymentAmt, curUser.getUserId(), paymentActId, createdDate.getWsDateStamp());                
                changedBalance.adjustBalance(payeeActId, debitAmt);
            } else {
                errorTxt.setErrorText("Insufficient funds !");
            }
        } else {
            errorTxt.setErrorText("Something gone wrong with my accounts !");
        }
        debugTxt.setDebugText("Amount paid " + paymentAmt + " to " + payeeUsername);
        return "home";
    }
    
}
