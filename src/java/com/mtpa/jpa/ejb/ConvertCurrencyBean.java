//310314    MtpA    Created

package com.mtpa.jpa.ejb;

import com.mtpa.jpa.iface.ConvertCurrencyLocal;
import com.mtpa.jpa.iface.RESTfulClientLocal;
import com.mtpa.jpa.enums.CurrencyEnum;
import com.mtpa.jpa.enums.ReqAcceptEnum;
import com.mtpa.jpa.enums.ReqTypeEnum;
import com.mtpa.jpa.iface.RESTfulXMLExtractLocal;
import com.mtpa.jpa.jsf.JSFErrorBean;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
@Local(ConvertCurrencyLocal.class)
public class ConvertCurrencyBean implements ConvertCurrencyLocal {
    
    @Inject
    JSFErrorBean errorTxt;
    
    @EJB
    RESTfulClientLocal restfulClient;
    @EJB
    RESTfulXMLExtractLocal convAmt;
    
    private static final String RESTFUL_URI_ROOT = "http://localhost:8080/RestFULAssignment/conversion/";

    public ConvertCurrencyBean() {
        
    }

    @Override
    public double ConvertCurrency(double vAmount, CurrencyEnum vCurrencyFrom, CurrencyEnum vCurrencyTo) {
        String restfulUrl = RESTFUL_URI_ROOT + vCurrencyFrom.getValidCurrency() + "/" + vCurrencyTo.getValidCurrency() + "/" + vAmount;
        try {
            String xmlResponse = restfulClient.restfulRequest(restfulUrl, ReqAcceptEnum.XML, ReqTypeEnum.GET, "");
            return convAmt.getConversion(xmlResponse);
        } catch (Exception e) {
            errorTxt.setErrorText("It's all gone Pete Tong");
            System.out.println(e);
            return -1;
        }        
    }
}
