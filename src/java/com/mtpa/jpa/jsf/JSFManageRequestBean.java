//270314    MtpA    Created

package com.mtpa.jpa.jsf;

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
    
    public String submitManRequest() {
        switch(reqStatus) {
            case PENDING:
                break;
            case REJECTED:
                changeRequestStatus();
                break;
            case CONFIRMED:
                changeRequestStatus();
                makePayment();
                break;
            default:
                break;
        }
        return "home";
    }
    
    private void changeRequestStatus() {
        String[] selectionArray = selectedRequest.split(":");
        try {
            requestId = Long.parseLong(selectionArray[0]);
            userRequest.updateStatus(requestId, reqStatus);
        } catch (NumberFormatException e) {
            errorTxt.setErrorText("Problem with the user record");
        }
    }
    
    private void makePayment() {
        for (ENTRequest curReq : requestList) {
            if (curReq.getId() == requestId) {
                double requestAmt = curReq.getRequestAmt();
                ENTAccount myAccount = account.getSingleAccount(selectedAccount);
                //convert the requestors amount into my currency and debit from my account
                double debitAmt = 0 - convAmt.ConvertCurrency(requestAmt, curReq.getCurrency(), myAccount.getAcctCurrency());
                accountPayment.paymentTransaction(myAccount.getId(), curReq.getRequestorId(), curReq.getAccountId(), debitAmt);                
                //add request amount in original currency to tp account
                accountPayment.paymentTransaction(curReq.getAccountId(), curUser.getUserId(), myAccount.getId(), requestAmt);
                break;
            }
        }
    }
}
