//290314    MtpA    Created

package com.mtpa.jpa.ejb;

import com.mtpa.jpa.iface.AdjustBalanceLocal;
import com.mtpa.jpa.entity.ENTAccount;
import com.mtpa.jpa.iface.AccountJPALocal;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Local(AdjustBalanceLocal.class)
public class AdjustBalanceBean implements AdjustBalanceLocal {

    @PersistenceContext
    EntityManager balanceEm;
    
    @EJB
    AccountJPALocal balanceAcct;
    
    public AdjustBalanceBean() {
        
    }
    
    @Override
    public void adjustBalance(long vAccountId, double vAmount) {
        ENTAccount balanceAcct = balanceEm.find(ENTAccount.class, vAccountId);
        if (balanceAcct != null) {
            vAmount = vAmount + balanceAcct.getBalance();
            balanceAcct.setBalance(vAmount);
        } else {
            System.out.println("Something gone astray with the balance");
        }
    }
}
