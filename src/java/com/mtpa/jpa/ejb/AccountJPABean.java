//270314    MtpA    Created

package com.mtpa.jpa.ejb;

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
    
    @PostConstruct
    public void postConstruct() {
        System.out.println("AccountStore: PostConstruct");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("AccountStore: PreDestroy");
    }
}
