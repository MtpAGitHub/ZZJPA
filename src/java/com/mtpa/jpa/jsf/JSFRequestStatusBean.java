//020414    MtpA    Added filter for status
//310314    MtpA    Created

package com.mtpa.jpa.jsf;

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
    
    //filter out the PENDING status as you can only CONFIRM or REJECT
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