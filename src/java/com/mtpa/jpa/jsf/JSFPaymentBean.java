//270314    MtpA    Created

package com.mtpa.jpa.jsf;

import com.mtpa.jpa.entity.ENTAccount;
import com.mtpa.jpa.entity.ENTTransaction_;
import com.mtpa.jpa.entity.ENTUser;
import com.mtpa.jpa.iface.AccountJPALocal;
import com.mtpa.jpa.iface.AdjustBalanceLocal;
import com.mtpa.jpa.iface.DateStampLocal;
import com.mtpa.jpa.iface.GetTPUserLocal;
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
    @EJB
    GetTPUserLocal tpUserList;
    
    private String payorActName;
    private long payorActId;
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

    public String getPayorActName() {
        return payorActName;
    }

    public void setPayorActName(String paymentActName) {
        this.payorActName = paymentActName;
    }

    public long getPayorActId() {
        return payorActId;
    }

    public void setPayorActId(long payorActId) {
        this.payorActId = payorActId;
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
        return tpUserList.getTPUserList(curUser.getUserId());
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
            accountNameList = new ArrayList<>();
            for (ENTAccount curAcct : acctList) {
                accountNameList.add(curAcct.getAccountName());
            }
        } else {
            accountNameList.add("NoRecordsFound");
        }        
    }

    public String submitPayment() {
        ENTAccount payorAcct = userAcct.getSingleAccount(payorActName);
        if (payorAcct != null) {
            if (payorAcct.getBalance() - paymentAmt > 0) {
                //take money out of my account
                payorActId = payorAcct.getId();
                double debitAmt = 0 - paymentAmt;
                ENTUser selectedUser = tpUser.getUser(payeeUsername);
                if (selectedUser != null) {
                    payeeUserId = selectedUser.getPersonId();
                    ENTAccount selectedAcct = userAcct.getSingleAccount(payeeActName);
                    if (selectedAcct != null) {
                        payeeActId = selectedAcct.getId();
                        paymentTrans.createTransaction(payorActId, debitAmt, payeeUserId, payeeActId, createdDate.getWsDateStamp());
                        changedBalance.adjustBalance(payorActId, debitAmt);
                        //pay money into their account
                        paymentTrans.createTransaction(payeeActId, paymentAmt, curUser.getUserId(), payorActId, createdDate.getWsDateStamp());                
                        changedBalance.adjustBalance(payeeActId, paymentAmt);                                            
                    } else {
                        errorTxt.setErrorText("Cannot find payee account record");
                    }
                } else {
                    errorTxt.setErrorText("Cannot find payee record");
                }
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
