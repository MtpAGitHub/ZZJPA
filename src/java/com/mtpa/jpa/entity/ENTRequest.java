/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mtpa.jpa.entity;

/**
 *
 * @author MtpA
 * 040414   Added queries to get requests made to and for me
 * 020414   Added many-many with ENTUser
 * 230314   Created entity
 */
import com.mtpa.jpa.enums.CurrencyEnum;
import com.mtpa.jpa.enums.RequestStatusEnum;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

//findAllRequests - all requests in the table
//findAllPendingRequests - all requests that have a status of PENDING
//findRequestsByRequestor - all requests where a user id is equal to the stored requestor id
//findRequestsByRequestee - all requests where a user id is equal to the stored requestee id
@Entity
@NamedQueries({
    @NamedQuery(name="findAllRequests",query="SELECT req FROM ENTRequest req"),
    @NamedQuery(name="findAllPendingRequests", query="SELECT req FROM ENTRequest req WHERE req.requestStatus = :status"),
    @NamedQuery(name="findRequestsByRequestor", query="SELECT req FROM ENTRequest req WHERE req.requestorId = :userid"),
    @NamedQuery(name="findRequestsByRequestee", query="SELECT req FROM ENTRequest req WHERE req.requesteeId = :userid")
})
public class ENTRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    //link to users table as a request can relate to more than one user (requestor and requestee)
    @ManyToMany(mappedBy = "request")
    List<ENTUser> user;
    
    @NotNull
    private Long requestorId;
    
    @NotNull
    private Long requesteeId;
    
    @NotNull
    private double requestAmt;
    
    @NotNull
    private long accountId;
    
    @NotNull
    private CurrencyEnum currency;
    
    @NotNull
    private RequestStatusEnum requestStatus;
    
    @NotNull
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date requestDate;

    public ENTRequest() {
        
    }
    
    public ENTRequest(long vRequestorId, long vRequesteeId, double vAmount, long vRequesteeAccId, CurrencyEnum vCurrency, Date vCreateDate) {
        this.requestorId = vRequestorId;
        this.requesteeId = vRequesteeId;
        this.requestAmt = vAmount;
        this.accountId = vRequesteeAccId;
        this.requestStatus = RequestStatusEnum.PENDING;
        this.currency = vCurrency;
        this.requestDate = vCreateDate;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRequestorId() {
        return requestorId;
    }

    public void setRequestorId(Long requestorId) {
        this.requestorId = requestorId;
    }

    public Long getRequesteeId() {
        return requesteeId;
    }

    public void setRequesteeId(Long requesteeId) {
        this.requesteeId = requesteeId;
    }

    public double getRequestAmt() {
        return requestAmt;
    }

    public void setRequestAmt(double requestAmt) {
        this.requestAmt = requestAmt;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public RequestStatusEnum getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatusEnum requestStatus) {
        this.requestStatus = requestStatus;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public CurrencyEnum getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEnum currency) {
        this.currency = currency;
    }

    public List<ENTUser> getUser() {
        return user;
    }

    public void setUser(List<ENTUser> user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.id);
        hash = 13 * hash + Objects.hashCode(this.user);
        hash = 13 * hash + Objects.hashCode(this.requestorId);
        hash = 13 * hash + Objects.hashCode(this.requesteeId);
        hash = 13 * hash + (int) (Double.doubleToLongBits(this.requestAmt) ^ (Double.doubleToLongBits(this.requestAmt) >>> 32));
        hash = 13 * hash + (int) (this.accountId ^ (this.accountId >>> 32));
        hash = 13 * hash + Objects.hashCode(this.currency);
        hash = 13 * hash + Objects.hashCode(this.requestStatus);
        hash = 13 * hash + Objects.hashCode(this.requestDate);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ENTRequest other = (ENTRequest) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.requestorId, other.requestorId)) {
            return false;
        }
        if (!Objects.equals(this.requesteeId, other.requesteeId)) {
            return false;
        }
        if (Double.doubleToLongBits(this.requestAmt) != Double.doubleToLongBits(other.requestAmt)) {
            return false;
        }
        if (this.accountId != other.accountId) {
            return false;
        }
        if (this.currency != other.currency) {
            return false;
        }
        if (this.requestStatus != other.requestStatus) {
            return false;
        }
        if (!Objects.equals(this.requestDate, other.requestDate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTRequest{" + "id=" + id + ", user=" + user + ", requestorId=" + requestorId + ", requesteeId=" + requesteeId + ", requestAmt=" + requestAmt + ", accountId=" + accountId + ", currency=" + currency + ", requestStatus=" + requestStatus + ", requestDate=" + requestDate + '}';
    }


}
