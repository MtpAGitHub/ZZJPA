/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtpa.jpa.iface;

/**
 *
 * @author MtpA
 * 090414   Now throw all exception on adjustBalance rather than catch as nothing can be done at this point to recover
 * 270314   Created class
 */
import com.mtpa.jpa.entity.ENTAccount;
import com.mtpa.jpa.enums.CurrencyEnum;
import java.util.List;

public interface AccountJPALocal {

    void createAccount(long vUserId, String vAccountName, double vBalance, CurrencyEnum vCurrency);

    void adjustBalance(long vAccountId, double vAmount) throws Exception;
    
    ENTAccount getSingleAccount(String vAccountName);

    boolean accountExist(String vAccountName);
    
    List<ENTAccount> getAccountList();

    List<ENTAccount> getUserAccountList(long vUserId);
    
}
