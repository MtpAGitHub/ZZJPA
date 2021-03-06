/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtpa.jpa.jsf;

/**
 *
 * @author MtpA
 * 260314   Created to display the available currency values on the screen through a drop down list
 */
import com.mtpa.jpa.enums.CurrencyEnum;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named("prefcurrency")
@RequestScoped
public class JSFCurrencyBean {

    public JSFCurrencyBean() {
        
    }
    
    // create an array of the enum values which will be rendered as the selectOneMenu items
    public CurrencyEnum[] getPreferredCurrency() {
        return CurrencyEnum.values();
    }
}
