//260314    MtpA    Created

package com.mtpa.jpa.jsf;

import com.mtpa.jpa.entity.ENTAccount;
import com.mtpa.jpa.enums.CurrencyEnum;
import com.mtpa.jpa.iface.AccountJPALocal;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("account")
@RequestScoped
public class JSFAcctBean {

    @EJB
    AccountJPALocal accountDet;
    
    @Inject
    JSFErrorBean errorTxt;
    @Inject
    JSFDebugBean debugTxt;
    @Inject
    JSFUserBean curUser;
    
    private String accountName;
    private CurrencyEnum accountCur;
    private double accountBal = 1000000;        //should be STATIC FINAL but cannot access from JSF page
    
    public JSFAcctBean() {
        
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public CurrencyEnum getAccountCur() {
        return accountCur;
    }

    public void setAccountCur(CurrencyEnum accountCur) {
        this.accountCur = accountCur;
    }

    public double getAccountBal() {
        return accountBal;
    }

    public void setAccountBal(double accountBal) {
        this.accountBal = accountBal;
    }

    public String submitAccount() {
        if (!accountDet.accountExist(accountName)) {
            accountDet.createAccount(curUser.getUserId(), accountName, accountBal, accountCur);
            return "home";
        } else {
            errorTxt.setErrorText("Account already exists");
            return "home";
        }
    }
}
