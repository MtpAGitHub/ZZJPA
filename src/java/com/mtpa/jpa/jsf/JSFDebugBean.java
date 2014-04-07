/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mtpa.jpa.jsf;

 /**
 *
 * @author MtpA
 * 140314   Created bean to allow me to show debug messages in a consistent manner
 *
 */
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
