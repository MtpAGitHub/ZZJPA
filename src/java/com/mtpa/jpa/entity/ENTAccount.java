/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mtpa.jpa.entity;

/**
 *
 * @author MtpA
 * 020414   Added annotations to link as a many to one to ENTUser and a one to many for ENTTransaction
 * 280314   Created entity
 */
import com.mtpa.jpa.enums.CurrencyEnum;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

// Using NamedQueries annotation as got more than one.  Tried Google on 'multiple named queries'
// Oracle documentation just came back with single NamedQuery examples and no obvious link anywhere else
// http://www.objectdb.com/java/jpa/query/named came back with @NamedQueries annotation below

//all queries should be self explanatory
//findAllAccounts - every single account in the table
//findUserAccounts - accounts that relate to a specified user id
//findSingleAccount - a single account that has a specific account name
@Entity
@NamedQueries({
    @NamedQuery(name="findAllAccounts",query="SELECT acct FROM ENTAccount acct"),
    @NamedQuery(name="findUserAccounts",query="SELECT acct FROM ENTAccount acct WHERE acct.userId = :userid"),
    @NamedQuery(name="findSingleAccount", query = "SELECT acct FROM ENTAccount acct WHERE acct.accountName = :acctname")
})
public class ENTAccount implements Serializable {
    private static final long serialVersionUID = 1L;
    //primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //link to parent ENTUser table via a many to one
    @ManyToOne
    @JoinColumn
    private ENTUser user;

    // link to the ENTTransaction table through a one to many relationship
    @OneToMany(mappedBy = "account")
    private List<ENTTransaction> transaction;

    @NotNull
    private Long userId;
    
    @NotNull
    private String accountName;
    
    @NotNull
    private double balance;
    
    @NotNull
    private CurrencyEnum acctCurrency;
    
    public ENTAccount() {
        
    }

    //constructor with args to create a new instance
    public ENTAccount(long vUserId, String vAccountName, double vBalance, CurrencyEnum vCurrency) {
        this.userId = vUserId;
        this.accountName = vAccountName;
        this.balance = vBalance;
        this.acctCurrency = vCurrency;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public CurrencyEnum getAcctCurrency() {
        return acctCurrency;
    }

    public void setAcctCurrency(CurrencyEnum acctCurrency) {
        this.acctCurrency = acctCurrency;
    }

    public ENTUser getUser() {
        return user;
    }

    public void setUser(ENTUser user) {
        this.user = user;
    }

    public List<ENTTransaction> getTransaction() {
        return transaction;
    }

    public void setTransaction(List<ENTTransaction> transaction) {
        this.transaction = transaction;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.id);
        hash = 43 * hash + Objects.hashCode(this.user);
        hash = 43 * hash + Objects.hashCode(this.transaction);
        hash = 43 * hash + Objects.hashCode(this.userId);
        hash = 43 * hash + Objects.hashCode(this.accountName);
        hash = 43 * hash + (int) (Double.doubleToLongBits(this.balance) ^ (Double.doubleToLongBits(this.balance) >>> 32));
        hash = 43 * hash + Objects.hashCode(this.acctCurrency);
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
        final ENTAccount other = (ENTAccount) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.transaction, other.transaction)) {
            return false;
        }
        if (!Objects.equals(this.userId, other.userId)) {
            return false;
        }
        if (!Objects.equals(this.accountName, other.accountName)) {
            return false;
        }
        if (Double.doubleToLongBits(this.balance) != Double.doubleToLongBits(other.balance)) {
            return false;
        }
        if (this.acctCurrency != other.acctCurrency) {
            return false;
        }
        return true;
    }

}
