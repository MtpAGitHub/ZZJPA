//270314    MtpA    Created

package com.mtpa.jpa.jsf;

import com.mtpa.jpa.entity.ENTRequest;
import com.mtpa.jpa.entity.ENTUser;
import com.mtpa.jpa.enums.RequestStatusEnum;
import com.mtpa.jpa.iface.RequestJPALocal;
import com.mtpa.jpa.iface.UserJPALocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("manrequest")
@RequestScoped
public class JSFManageRequestBean {

    @Inject
    JSFDebugBean debugTxt;
    @Inject
    JSFErrorBean errorTxt;
    @Inject
    JSFUserBean curUser;
    
    @EJB
    RequestJPALocal userRequest;
    @EJB
    UserJPALocal tpUser;
    
    private ENTRequest pendRequest;
    private RequestStatusEnum reqStatus;
    private String selectedRequest;

    public JSFManageRequestBean() {
        
    }

    public ENTRequest getPendRequest() {
        return pendRequest;
    }

    public void setPendRequest(ENTRequest pendRequest) {
        this.pendRequest = pendRequest;
    }

    public RequestStatusEnum getReqStatus() {
        return reqStatus;
    }

    public void setReqStatus(RequestStatusEnum reqStatus) {
        this.reqStatus = reqStatus;
    }

    public String getSelectedRequest() {
        return selectedRequest;
    }

    public void setSelectedRequest(String selectedRequest) {
        this.selectedRequest = selectedRequest;
    }

    public List<String> getAllPendingRequests() {
        List<ENTRequest> requestList = userRequest.getPendingList(reqStatus.PENDING);
        List<String> reqDetailsList = new ArrayList<>();
        if (requestList.size() > 0) {
            for (ENTRequest curReq : requestList) {
                ENTUser userDetail = tpUser.getUserById(curReq.getRequestorId());
                String requestDetail = curReq.getId().toString() + ": " + userDetail.getUsername() + " : " + curReq.getRequestAmt();
                reqDetailsList.add(requestDetail);
            }
        } else {
            reqDetailsList.add("NoRecordsFound");
        }
        return reqDetailsList;
    }
    
    public String submitManRequest() {
        switch(reqStatus) {
            case PENDING:
                break;
            case REJECTED:
                break;
            case CONFIRMED:
                break;
            default:
                break;
        }
        debugTxt.setDebugText("You have " + reqStatus + " the request " + selectedRequest);
        return "home";
    }
    
    private void changeRequestStatus(RequestStatusEnum vRequestStatus, String vSelection) {
        String[] selectionArray = vSelection.split(":");
        try {
            long userId = Long.parseLong(selectionArray[0]);
            
        } catch (NumberFormatException e) {
            errorTxt.setErrorText("Problem with the user record");
        }
    }
}
