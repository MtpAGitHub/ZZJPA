//310314    MtpA    Created - Made as class as called from both payment and request

package com.mtpa.jpa.ejb;

import com.mtpa.jpa.iface.PaymentLocal;
import com.mtpa.jpa.iface.AccountJPALocal;
import com.mtpa.jpa.iface.DateStampLocal;
import com.mtpa.jpa.iface.TransactionJPALocal;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(PaymentLocal.class)
public class PaymentBean implements PaymentLocal {

    @EJB
    TransactionJPALocal paymentTrans;
    @EJB
    AccountJPALocal changedBalance;
    @EJB
    DateStampLocal createdDate;
    
    public PaymentBean() {
        
    }
    
    @Override
    public void paymentTransaction(long vHomeActId, long vTpUserId, long vTpActId, double vAmount) {
        paymentTrans.createTransaction(vHomeActId, vAmount, vTpUserId, vTpActId, createdDate.getWsDateStamp());
        changedBalance.adjustBalance(vHomeActId, vAmount);
    }
}
