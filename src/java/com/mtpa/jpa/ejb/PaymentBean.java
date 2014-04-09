/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mtpa.jpa.ejb;

/**
 *
 * @author MtpA
 * 090414   Added exception throw to pass back to user
 * 310314   Created class as used in more than one place
 */
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

    //create a payment transaction (can be 'in' or 'out' depending on values in args and then change the account balance
    @Override
    public void paymentTransaction(long vHomeActId, long vTpUserId, long vTpActId, double vAmount) throws Exception {
        paymentTrans.createTransaction(vHomeActId, vAmount, vTpUserId, vTpActId, createdDate.getWsDateStamp());
        changedBalance.adjustBalance(vHomeActId, vAmount);
    }
}
