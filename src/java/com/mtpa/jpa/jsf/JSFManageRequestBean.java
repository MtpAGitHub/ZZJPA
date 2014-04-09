/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mtpa.jpa.jsf;

 /**
 *
 * @author MtpA
 * 090414   Added exception try/catch if can't convert the currency & added formResponse to submitManRequest to deal with exceptions
 * 270314   Created to manage the requests made from another user
 *
 */
import com.mtpa.jpa.entity.ENTAccount;
import com.mtpa.jpa.entity.ENTRequest;
import com.mtpa.jpa.entity.ENTUser;
import com.mtpa.jpa.enums.RequestStatusEnum;
import com.mtpa.jpa.iface.AccountJPALocal;
import com.mtpa.jpa.iface.ConvertCurrencyLocal;
import com.mtpa.jpa.iface.GetAccountListLocal;
import com.mtpa.jpa.iface.PaymentLocal;
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

     //uses JSF backing beans from the JSF container
    @Inject
    JSFDebugBean debugTxt;
    @Inject
    JSFErrorBean errorTxt;
    @Inject
    JSFUserBean curUser;
    
     //uses backing beans in the business container (to do the business logic)
    @EJB
    RequestJPALocal userRequest;
    @EJB
    UserJPALocal tpUser;
    @EJB
    GetAccountListLocal accountList;
    @EJB
    AccountJPALocal account;
    @EJB
    PaymentLocal accountPayment;
    @EJB
    ConvertCurrencyLocal convAmt;
    
    private ENTRequest pendRequest;
    private RequestStatusEnum reqStatus;
    private String selectedRequest;
    private String selectedAccount;
    private List<ENTRequest> requestList;
    private long requestId;

    public JSFManageRequestBean() {
        
    }

    //standard getters & setters
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

    public String getSelectedAccount() {
        return selectedAccount;
    }

    public void setSelectedAccount(String selectedAccount) {
        this.selectedAccount = selectedAccount;
    }

    public List<ENTRequest> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<ENTRequest> requestList) {
        this.requestList = requestList;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    //when looking to manage requests made of us we are only interested in PENDING ones as they have not yet been processed
    //get all requests with a PENDING status and then process them into a string to show request id:username:amount:currency
    //this is done to make the list readable on the screen
    public List<String> getAllPendingRequests() {
        requestList = userRequest.getPendingList(reqStatus.PENDING);
        List<String> reqDetailsList = new ArrayList<>();
        if (requestList.size() > 0) {
            for (ENTRequest curReq : requestList) {
                ENTUser userDetail = tpUser.getUserById(curReq.getRequestorId());
                String requestDetail = curReq.getId().toString() + ": " + userDetail.getUsername() + " : " + curReq.getRequestAmt() + " : " + curReq.getCurrency();
                reqDetailsList.add(requestDetail);
            }
        } else {
            reqDetailsList.add("NoRecordsFound");
        }
        return reqDetailsList;
    }

    public List<String> getMyAccounts() {
        return accountList.myAccountList(curUser.getUserId());            
    }
    
    //when submitting a request management do different things based on status
    //pending = nothing
    //rejected = just update the status (no payments)
    //confirmed = update the status and make a payment to the selected user/account
    public String submitManRequest() {
        String formResponse;
        switch(reqStatus) {
            case PENDING:
                break;
            case REJECTED:
                formResponse = changeRequestStatus();
                break;
            case CONFIRMED:
                formResponse = changeRequestStatus();
                makePayment();
                break;
            default:
                break;
        }
        return "home";
    }
    
    //change the status on the request for payment
    private String changeRequestStatus() {
        //split the drop down item into array using : as a delimiter
        String[] selectionArray = selectedRequest.split(":");
        try {
            requestId = Long.parseLong(selectionArray[0]);
            userRequest.updateStatus(requestId, reqStatus);
            return "home";
        } catch (Exception e) {
            errorTxt.setErrorText("Problem with amending the status");
            return null;
        }
    }
    
    //this will loop through the list of requests until we match the one we picked (there will be a better way of doing this I am sure)
    //when found the one we want then take the money from our account (converting the amount to our currency first)
    //then make a payment to the third party account in the original (requested) currency
    private void makePayment() {
        for (ENTRequest curReq : requestList) {
            if (curReq.getId() == requestId) {
                double requestAmt = curReq.getRequestAmt();
                ENTAccount myAccount = account.getSingleAccount(selectedAccount);
                //convert the requestors amount into my currency and debit from my account (catches thrown exception from currency bean)
                try {
                    double debitAmt = 0 - convAmt.ConvertCurrency(requestAmt, curReq.getCurrency(), myAccount.getAcctCurrency());
                    accountPayment.paymentTransaction(myAccount.getId(), curReq.getRequestorId(), curReq.getAccountId(), debitAmt);                
                    //add request amount in original currency to tp account
                    accountPayment.paymentTransaction(curReq.getAccountId(), curUser.getUserId(), myAccount.getId(), requestAmt);
                } catch (Exception ex) {
                    errorTxt.setErrorText("Error in converting the currency !");
                }
                break;
            }
        }
    }
}
