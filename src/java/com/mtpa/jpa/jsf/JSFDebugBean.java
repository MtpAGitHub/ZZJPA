//140314    MtpA    Created

package com.mtpa.jpa.jsf;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named("debug")
@RequestScoped
public class JSFDebugBean {

    private String debugText;
    
    public JSFDebugBean() {
    }

    public String getDebugText() {
        return debugText;
    }

    public void setDebugText(String debugText) {
        this.debugText = debugText;
    }
    
}
