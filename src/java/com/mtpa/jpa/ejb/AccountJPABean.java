/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mtpa.jpa.ejb;

/**
 *
 * @author MtpA
 * 090414   Added throw of exception as failure to adjust balance needs to be told to user
 * 270314   Created class
 */
import com.mtpa.jpa.iface.AccountJPALocal;
import com.mtpa.jpa.entity.ENTAccount;
import com.mtpa.jpa.enums.CurrencyEnum;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@Local(AccountJPALocal.class)
public class AccountJPABean implements AccountJPALocal {

    @PersistenceContext
    EntityManager accountEm;
    
    public AccountJPABean() {
        
    }
    
    @Override
    public synchronized List<ENTAccount> getAccountList() {
        return accountEm.createNamedQuery("findAllAccounts").getResultList();
    }
    
    @Override
    public synchronized List<ENTAccount> getUserAccountList(long vUserId) {
        Query findUserAcct = accountEm.createNamedQuery("findUserAccounts");
        findUserAcct.setParameter("userid", vUserId);
        return findUserAcct.getResultList();
    }

    //getting list rather than single item as cannot be sure that you will only get one result
    @Override
    public synchronized ENTAccount getSingleAccount(String vAccountName) {
        Query findSingleAcct = accountEm.createNamedQuery("findSingleAccount");
        findSingleAcct.setParameter("acctname", vAccountName);
        List<ENTAccount> acctDetail = findSingleAcct.getResultList();
        if (!acctDetail.isEmpty()) {
            return acctDetail.get(0);
        } else {
            return null;
        }
    }
    
    @Override
    public synchronized boolean accountExist(String vAccountName) {
        Query findSingleAcct = accountEm.createNamedQuery("findSingleAccount");
        findSingleAcct.setParameter("acctname", vAccountName);
        List<ENTAccount> acctDetail = findSingleAcct.getResultList();
        if (!acctDetail.isEmpty()) {
            return true;
        } else {
            return false;
        }        
    }
    
    @Override
    public synchronized void createAccount(long vUserId, String vAccountName, double vBalance, CurrencyEnum vCurrency) {
        ENTAccount account = new ENTAccount(vUserId, vAccountName, vBalance, vCurrency);
        accountEm.persist(account);
    }

    @Override
    public void adjustBalance(long vAccountId, double vAmount) throws Exception {        
        ENTAccount balanceAcct = accountEm.find(ENTAccount.class, vAccountId);
        if (balanceAcct != null) {
            vAmount = vAmount + balanceAcct.getBalance();
            accountEm.persist(balanceAcct);
            balanceAcct.setBalance(vAmount);
        } else {
            throw new Exception();
        }
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("AccountStore: PostConstruct");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("AccountStore: PreDestroy");
    }
}
