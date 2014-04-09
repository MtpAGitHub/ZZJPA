/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mtpa.jpa.ejb;

/**
 *
 * @author MtpA
 * 040414   Added call to get your own transactions (as per spec)
 * 280314   Created class to manage transactions
 */
import com.mtpa.jpa.iface.TransactionJPALocal;
import com.mtpa.jpa.entity.ENTTransaction;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@Local(TransactionJPALocal.class)
public class TransactionJPABean implements TransactionJPALocal {

    @PersistenceContext
    EntityManager transactionEm;
    
    public TransactionJPABean() {
        
    }
    
    @Override
    public synchronized List<ENTTransaction> getTransactionList() {
        return transactionEm.createNamedQuery("findAllTransactions").getResultList();
    }

    //parameter used so that can narrow down data search
    @Override
    public synchronized List<ENTTransaction> getTransByAcctId(List<Long> vAcctList) {
        Query myTransactions = transactionEm.createNamedQuery("findTransactionByAccountId");
        myTransactions.setParameter("acctlist", vAcctList);
        return myTransactions.getResultList();        
    }
    
    @Override
    public synchronized void createTransaction(long vAccountId, double vAmount, long vTPUserId, long vTPAccoutId, Date vCreatedDate) {
        ENTTransaction outTransaction = new ENTTransaction(vAccountId, vAmount, vTPUserId, vTPAccoutId, vCreatedDate);
        transactionEm.persist(outTransaction);
    }
    
    @PostConstruct
    public void postConstruct() {
        System.out.println("TransactionStore: PostConstruct");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("TransactionStore: PreDestroy");
    }
}
