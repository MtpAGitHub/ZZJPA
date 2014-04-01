/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mtpa.jpa.iface;

import java.util.List;

/**
 *
 * @author MtpA
 */
public interface GetAccountListLocal {

    void getAccountList(long acctListUserId);

    List<String> myAccountList(long vUserId);

    List<String> tpAccountList(String vUsername);
    
}
