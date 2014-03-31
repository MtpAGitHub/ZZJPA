//310314    MtpA    Created.  Made into common class as used from both payment and request classes
package com.mtpa.jpa.ejb;

import com.mtpa.jpa.iface.GetTPUserLocal;
import com.mtpa.jpa.entity.ENTUser;
import com.mtpa.jpa.iface.UserJPALocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class GetTPUserBean implements GetTPUserLocal {

    @EJB
    UserJPALocal tpUser;
    
    public GetTPUserBean() {

    }

    @Override
    public List<String> getTPUserList(long vUserId) {
        List<ENTUser> userList = tpUser.getAllTpUsers(vUserId);
        List<String> usernameList = new ArrayList<>();
        if (userList.size() > 0) {
            for (ENTUser curUser : userList) {
                usernameList.add(curUser.getUsername());
            }
        } else {
            usernameList.add("NoRecordsFound");
        }
        return usernameList;
    }
}
