//010414    MtpA    Created

package com.mtpa.jpa.ejb;

import com.mtpa.jpa.iface.GetAccountListLocal;
import com.mtpa.jpa.entity.ENTAccount;
import com.mtpa.jpa.entity.ENTUser;
import com.mtpa.jpa.iface.AccountJPALocal;
import com.mtpa.jpa.iface.UserJPALocal;
import java.util.ArrayList;
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
    @Override
    public List<String> tpAccountList(String vUsername) {
        ENTUser selectedUser = tpUser.getUserByName(vUsername);
        accountNameList = new ArrayList<>();
        if (selectedUser !=null) {
            getAccountList(selectedUser.getPersonId());
        } else {
            accountNameList.add("NoRecordsFound");
        }
        return accountNameList;
    }

    @Override
    public List<String> myAccountList(long vUserId) {
        getAccountList(vUserId);
        return accountNameList;
    }
    
    @Override
    public void getAccountList(long acctListUserId) {
        List<ENTAccount> acctList = userAcct.getUserAccountList(acctListUserId);
        if (acctList.size() > 0) {
            accountNameList = new ArrayList<>();
            for (ENTAccount curAcct : acctList) {
                accountNameList.add(curAcct.getAccountName());
            }
        } else {
            accountNameList.add("NoRecordsFound");
        }        
    }
    
}
