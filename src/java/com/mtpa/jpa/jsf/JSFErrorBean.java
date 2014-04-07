/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mtpa.jpa.jsf;

 /**
 *
 * @author MtpA
 * 190314   Created bean to allow me to show error messages in a consistent manner
 *
 */
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named("error")
@RequestScoped
public class JSFErrorBean {
    
    private String errorText;
    
    public JSFErrorBean() {
        
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }
    
}
