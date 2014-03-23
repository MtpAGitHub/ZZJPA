/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mtpa.jpa.entity;

import com.mtpa.jpa.enums.CurrencyEnum;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 *
 * @author MtpA
 */
@Entity
public class ENTAccount implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long userId;
    
    @NotNull
    private String accountName;
    
    @NotNull
    private double balance;
    
    @NotNull
    private CurrencyEnum acctCurrency;
    
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.userId);
        hash = 37 * hash + Objects.hashCode(this.accountName);
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.balance) ^ (Double.doubleToLongBits(this.balance) >>> 32));
        hash = 37 * hash + Objects.hashCode(this.acctCurrency);
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

    @Override
    public String toString() {
        return "ENTAccount{" + "id=" + id + ", userId=" + userId + ", accountName=" + accountName + ", balance=" + balance + ", acctCurrency=" + acctCurrency + '}';
    }
    
}
