//280314    MtpA    Created

package com.mtpa.jpa.ejb;

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
    
    @Override
    public synchronized void createTransaction(long vAccountId, double vAmount, long vTPUserId, long vTPAccoutId, Date vCreatedDate) {
        ENTTransaction transaction = new ENTTransaction(vAccountId, vAmount, vTPUserId, vTPAccoutId, vCreatedDate);
        transactionEm.persist(transaction);
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
