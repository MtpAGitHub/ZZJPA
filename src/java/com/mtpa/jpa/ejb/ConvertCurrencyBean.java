/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mtpa.jpa.ejb;

/**
 *
 * @author MtpA
 * 090414   Error picked up during testing.  Now throw all exceptions rather than catch as nothing can be done at this point to recover
 * 310314   Created class
 */
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
    
    private static final String RESTFUL_URI_ROOT = "http://localhost:8080/119882RESTFulConverter/conversion/";

    public ConvertCurrencyBean() {
        
    }

    @Override
    public double ConvertCurrency(double vAmount, CurrencyEnum vCurrencyFrom, CurrencyEnum vCurrencyTo) throws Exception {
        //if the currency values are different then do a conversion otherwise just send back the same amount
        if (!vCurrencyFrom.equals(vCurrencyTo)) {
            //call the RESTful service
            String restfulUrl = RESTFUL_URI_ROOT + vCurrencyFrom.getValidCurrency() + "/" + vCurrencyTo.getValidCurrency() + "/" + vAmount;
            //process the body of the response
            String xmlResponse = restfulClient.restfulRequest(restfulUrl, ReqAcceptEnum.XML, ReqTypeEnum.GET, "");
            return convAmt.getConversion(xmlResponse);
        } else {
            return vAmount;
        }
    }
}
