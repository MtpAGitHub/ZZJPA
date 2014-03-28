//280314    MtpA    Created

package com.mtpa.jpa.ejb;

import com.mtpa.jpa.iface.RequestJPALocal;
import com.mtpa.jpa.entity.ENTRequest;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    public synchronized void createRequest(long vRequestorId, long vRequesteeId, double vAmount, Date vCreateDate) {
        ENTRequest request = new ENTRequest(vRequestorId, vRequesteeId, vAmount, vCreateDate);
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
