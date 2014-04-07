/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//
//
//
//

package com.mtpa.jpa.jsf;

/**
 *
 * @author MtpA
 * 020414    Add post construct so that accounts are pre-populated
 * 010414    Refactored to move account list functionality to EJB
 * 310314    Added currency conversion
 * 270314    Created
 */

import com.mtpa.jpa.entity.ENTAccount;
import com.mtpa.jpa.entity.ENTUser;
import com.mtpa.jpa.iface.AccountJPALocal;
import com.mtpa.jpa.iface.ConvertCurrencyLocal;
import com.mtpa.jpa.iface.GetAccountListLocal;
import com.mtpa.jpa.iface.GetTPUserLocal;
import com.mtpa.jpa.iface.PaymentLocal;
import com.mtpa.jpa.iface.UserJPALocal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

@Named("payment")
@RequestScoped
public class JSFPaymentBean {

     //uses JSF backing beans from the JSF container
    @Inject
    JSFDebugBean debugTxt;
    @Inject
    JSFErrorBean errorTxt;
    @Inject
    JSFUserBean curUser;

     //uses backing beans in the business container (to do the business logic)
    @EJB
    UserJPALocal tpUser;
    @EJB
    AccountJPALocal userAcct;
    @EJB
    GetTPUserLocal tpUserList;
    @EJB
    ConvertCurrencyLocal convertAmt;
    @EJB
    PaymentLocal acctPayment;
    @EJB
    GetAccountListLocal acctList;
    
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

    //standard getters & setters
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
        return acctList.tpAccountList(payeeUsername);
    }

    public List<String> getMyAccounts() {
        return acctList.myAccountList(curUser.getUserId());
    }
    
    //listens to the payment JSF form user list for a change (through onclick) and fires to allow the page to rerender with the new data
    public void userChangeListener(AjaxBehaviorEvent usernameEvent) {
        
    }

    //this will do the actual payments
    //check that we have a valid account to pay the money from (payor)
    //check that payor has enough money in the bank to make the payment
    //first take the money from my (payor) account
    //second convert the amount to the payee currency
    //third pay the money into their account
    public String submitPayment() {
        ENTAccount payorAcct = userAcct.getSingleAccount(payorActName);
        if (payorAcct != null) {
            if (payorAcct.getBalance() - paymentAmt > 0) {
                //take money out of my account
                payorActId = payorAcct.getId();
                ENTUser selectedUser = tpUser.getUserByName(payeeUsername);
                if (selectedUser != null) {
                    payeeUserId = selectedUser.getPersonId();
                    ENTAccount selectedAcct = userAcct.getSingleAccount(payeeActName);
                    if (selectedAcct != null) {
                        payeeActId = selectedAcct.getId();
                        double debitAmt = 0 - paymentAmt;
                        // take money from payor account
                        acctPayment.paymentTransaction(payorActId, payeeUserId, payeeActId, debitAmt);
                        // convert payment amount to payee account currency
                        paymentAmt = convertAmt.ConvertCurrency(paymentAmt, payorAcct.getAcctCurrency(),selectedAcct.getAcctCurrency());
                        // pay money into payee account
                        acctPayment.paymentTransaction(payeeActId, curUser.getUserId(), payorActId, paymentAmt);
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

    //this was added so that we always have a payee username ready to build JSF page lists from.  Get the first third party user returned
    @PostConstruct
    public void postConstruct() {
        payeeUsername = getTpUsers().get(0);
    }
}
