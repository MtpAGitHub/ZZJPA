/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mtpa.jpa.jsf;

 /**
 *
 * @author MtpA
 * 090414   Added try/catch block on currency conversion
 * 310314   Added the currency conversion
 * 260314   Created backing bean
 *
 */

import com.mtpa.jpa.enums.CurrencyEnum;
import com.mtpa.jpa.iface.AccountJPALocal;
import com.mtpa.jpa.iface.ConvertCurrencyLocal;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

@Named("account")
@RequestScoped
public class JSFAcctBean {

    //uses backing beans in the business container (to do the business logic)
    @EJB
    AccountJPALocal accountDet;
    @EJB
    ConvertCurrencyLocal convertAmt;
    
    //uses JSF backing beans from the JSF container
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

    //standard getters & setters
    
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

    //change of value listener on the currency drop down triggered by onclick.  Based on the selected currency show the right amount on screen
    public void currencyChangeListener(AjaxBehaviorEvent currencyEvent) {
        //catch any problem when trying to convert the currency
        try {
            setAccountBal(convertAmt.ConvertCurrency(1000000, CurrencyEnum.GBP, accountCur));
        } catch (Exception ex) {
            errorTxt.setErrorText("Error converting currency !");
        }
    }
    
    //response to pressing the submit button on the accout page.  Will check if there is already an account with the same name before creating
    public String submitAccount() {
        if (!accountDet.accountExist(accountName)) {
            // add new account and default 'from' currency always GBP and catch any exception where the conversion could not take place
            try {
                accountDet.createAccount(curUser.getUserId(), accountName, convertAmt.ConvertCurrency(accountBal, CurrencyEnum.GBP, accountCur), accountCur);
                return "home";
            } catch (Exception ex) {
                errorTxt.setErrorText("Error converting currency !");
                return null;
            }
        } else {
            errorTxt.setErrorText("Account already exists");
            return "home";
        }
    }
}
