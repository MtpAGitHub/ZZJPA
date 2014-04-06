/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mtpa.jpa.jsf;

/**
 *
 * @author MtpA
 * 020414   Added the filter to drop out the PENDING status
 * 310314   Created the bean to display the status values in drop down list
 */

import com.mtpa.jpa.enums.RequestStatusEnum;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named("reqstatus")
@RequestScoped
public class JSFRequestStatusBean {

    public JSFRequestStatusBean() {
        
    }
    
    //Create an array of available status codes which are then shown on the screen
    //filter out the PENDING status as you can only CONFIRM or REJECT in the selectOneMenu JSF page
    public List<RequestStatusEnum> getRequestStatus() {
        RequestStatusEnum[] requestStatuses = RequestStatusEnum.values();
        List<RequestStatusEnum> nonPending = new ArrayList<>();
        for (int i = 0; i < requestStatuses.length; i++) {
            if (!requestStatuses[i].equals(RequestStatusEnum.PENDING)) {
                nonPending.add(requestStatuses[i]);
            }
        }
        return nonPending;
    }
}