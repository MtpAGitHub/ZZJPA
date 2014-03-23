//190314    MtpA    Created

package com.mtpa.jpa.jsf;

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
