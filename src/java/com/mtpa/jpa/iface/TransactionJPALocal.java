/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mtpa.jpa.iface;

import com.mtpa.jpa.entity.ENTTransaction;
import java.util.Date;
import java.util.List;

/**
 *
 * @author MtpA
 */
public interface TransactionJPALocal {

    void createTransaction(long vAccountId, double vAmount, long vTPUserId, long vTPAccoutId, Date vCreatedDate);

    List<ENTTransaction> getTransactionList();
    
}
