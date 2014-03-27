//270314    MtpA    Created

package com.mtpa.jpa.iface;

import com.mtpa.jpa.entity.ENTAccount;
import com.mtpa.jpa.enums.CurrencyEnum;
import java.util.List;

public interface AccountJPALocal {

    void createAccount(long vUserId, String vAccountName, double vBalance, CurrencyEnum vCurrency);

    List<ENTAccount> getAccountList();
    
}
