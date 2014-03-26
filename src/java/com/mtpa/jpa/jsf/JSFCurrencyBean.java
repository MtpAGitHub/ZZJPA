//260314    MtpA    Created

package com.mtpa.jpa.jsf;

import com.mtpa.jpa.enums.CurrencyEnum;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named("prefcurrency")
@RequestScoped
public class JSFCurrencyBean {

    public JSFCurrencyBean() {
        
    }
    
    public CurrencyEnum[] getPreferredCurrency() {
        return CurrencyEnum.values();
    }
}
