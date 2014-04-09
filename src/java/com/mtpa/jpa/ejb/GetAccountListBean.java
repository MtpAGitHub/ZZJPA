/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mtpa.jpa.ejb;

/**
 *
 * @author MtpA
 * 090414   Amended the class to return empty lists instead of 'no records found' see code comment
 * 010414    Created class
 */
import com.mtpa.jpa.iface.GetAccountListLocal;
import com.mtpa.jpa.entity.ENTAccount;
import com.mtpa.jpa.entity.ENTUser;
import com.mtpa.jpa.iface.AccountJPALocal;
import com.mtpa.jpa.iface.UserJPALocal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(GetAccountListLocal.class)
public class GetAccountListBean implements GetAccountListLocal {

    @EJB
    UserJPALocal tpUser;
    @EJB
    AccountJPALocal userAcct;
    
    private List<String> accountNameList;
    
    public GetAccountListBean() {
        
    }

    //this returns the list of accounts for a user (if can find one) in all other cases it returns an empty list
    //this means that I will be handling the display issues on the jsf page by means of rendered
    @Override
    public List<String> tpAccountList(String vUsername) {
        ENTUser selectedUser = tpUser.getUserByName(vUsername);
        accountNameList = new ArrayList<>();
        if (selectedUser !=null) {
            getAccountList(selectedUser.getPersonId());
            return accountNameList;
        } else {
            return Collections.<String>emptyList();
        }
    }

    @Override
    public List<String> myAccountList(long vUserId) {
        getAccountList(vUserId);
        return accountNameList;
    }
    
    //this returns the list of accounts for a user (if can find one) in all other cases it returns an empty list
    //this means that I will be handling the display issues on the jsf page by means of rendered
    @Override
    public void getAccountList(long acctListUserId) {
        List<ENTAccount> acctList = userAcct.getUserAccountList(acctListUserId);
        if (acctList.size() > 0) {
            for (ENTAccount curAcct : acctList) {
                accountNameList.add(curAcct.getAccountName());
            }
        } else {
            accountNameList = Collections.emptyList();
        }        
    }
    
}
