//280314    MtpA    Created

package com.mtpa.jpa.ejb;

import com.mtpa.jpa.iface.RequestJPALocal;
import com.mtpa.jpa.entity.ENTRequest;
import com.mtpa.jpa.enums.RequestStatusEnum;
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
@Local(RequestJPALocal.class)
public class RequestJPABean implements RequestJPALocal {

    @PersistenceContext
    EntityManager requestEm;
    
    public RequestJPABean() {
        
    }
    @Override
    public synchronized List<ENTRequest> getRequestList() {
        return requestEm.createNamedQuery("findAllRequests").getResultList();
    }
    
    @Override
    public synchronized List<ENTRequest> getPendingList(RequestStatusEnum vRequestStatus) {
        Query pendingRequests = requestEm.createNamedQuery("findAllPendingRequests");
        pendingRequests.setParameter("status", vRequestStatus);
        return pendingRequests.getResultList();        
    }
    
    @Override
    public synchronized void createRequest(long vRequestorId, long vRequesteeId, double vAmount, long vRequesteeAccId, Date vCreateDate) {
        ENTRequest request = new ENTRequest(vRequestorId, vRequesteeId, vAmount, vRequesteeAccId, vCreateDate);
        requestEm.persist(request);
    }
        
    @PostConstruct
    public void postConstruct() {
        System.out.println("RequestStore: PostConstruct");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("RequestStore: PreDestroy");
    }
}
