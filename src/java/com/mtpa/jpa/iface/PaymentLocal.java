/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mtpa.jpa.iface;

/**
 *
 * @author MtpA
 * 090414   Added exception throw as per class
 */
public interface PaymentLocal {

    void paymentTransaction(long vHomeActId, long vTpUserId, long vTpActId, double vAmount) throws Exception;
    
}
