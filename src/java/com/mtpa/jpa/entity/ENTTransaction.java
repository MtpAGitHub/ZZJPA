//020414    MtpA    Added ManyToOne link to accounts
//230314    MtpA    Created

package com.mtpa.jpa.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

@Entity
@NamedQueries({
    @NamedQuery(name="findAllTransactions",query="SELECT trans FROM ENTTransaction trans")
})
public class ENTTransaction implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    //link to accounts as many transactions can relate to a single account
    @ManyToOne
    @JoinColumn
    private ENTAccount account;

    @NotNull
    private Long accountId;
    
    @NotNull
    private double transactionAmt;
    
    @NotNull
    private Long tpUserId;
    
    @NotNull
    private Long tpAccountId;
    
    @NotNull
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date transactionDate;
    
    public ENTTransaction() {
        
    }
    
    public ENTTransaction(long vAccountId, double vAmount, long vTPUserId, long vTPAccoutId, Date vCreatedDate) {
        this.accountId = vAccountId;
        this.transactionAmt = vAmount;
        this.tpUserId = vTPUserId;
        this.tpAccountId = vTPAccoutId;
        this.transactionDate = vCreatedDate;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public double getTransactionAmt() {
        return transactionAmt;
    }

    public void setTransactionAmt(double transactionAmt) {
        this.transactionAmt = transactionAmt;
    }

    public Long getTpUserId() {
        return tpUserId;
    }

    public void setTpUserId(Long tpUserId) {
        this.tpUserId = tpUserId;
    }

    public Long getTpAccountId() {
        return tpAccountId;
    }

    public void setTpAccountId(Long tpAccountId) {
        this.tpAccountId = tpAccountId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public ENTAccount getAccount() {
        return account;
    }

    public void setAccount(ENTAccount account) {
        this.account = account;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.id);
        hash = 73 * hash + Objects.hashCode(this.account);
        hash = 73 * hash + Objects.hashCode(this.accountId);
        hash = 73 * hash + (int) (Double.doubleToLongBits(this.transactionAmt) ^ (Double.doubleToLongBits(this.transactionAmt) >>> 32));
        hash = 73 * hash + Objects.hashCode(this.tpUserId);
        hash = 73 * hash + Objects.hashCode(this.tpAccountId);
        hash = 73 * hash + Objects.hashCode(this.transactionDate);
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
        final ENTTransaction other = (ENTTransaction) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.account, other.account)) {
            return false;
        }
        if (!Objects.equals(this.accountId, other.accountId)) {
            return false;
        }
        if (Double.doubleToLongBits(this.transactionAmt) != Double.doubleToLongBits(other.transactionAmt)) {
            return false;
        }
        if (!Objects.equals(this.tpUserId, other.tpUserId)) {
            return false;
        }
        if (!Objects.equals(this.tpAccountId, other.tpAccountId)) {
            return false;
        }
        if (!Objects.equals(this.transactionDate, other.transactionDate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTTransaction{" + "id=" + id + ", account=" + account + ", accountId=" + accountId + ", transactionAmt=" + transactionAmt + ", tpUserId=" + tpUserId + ", tpAccountId=" + tpAccountId + ", transactionDate=" + transactionDate + '}';
    }
    
}
