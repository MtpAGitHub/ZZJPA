/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtpa.jpa.ejb;

/**
 *
 * @author MtpA
 * 090414   Amended so that it now returns an empty list rather than 'No records found'.  Easier to handle at front end
 * 310314   Created class.  Extracted from the JSF container and used as a business bean as used in more than one place
 */
import com.mtpa.jpa.iface.GetTPUserLocal;
import com.mtpa.jpa.entity.ENTUser;
import com.mtpa.jpa.iface.UserJPALocal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(GetTPUserLocal.class)
public class GetTPUserBean implements GetTPUserLocal {

    @EJB
    UserJPALocal tpUser;
    
    public GetTPUserBean() {

    }

    @Override
    public List<String> getTPUserList(long vUserId) {
        //get a list of users with a given Id.  Note a list is retrieved as you cannot guarantee that there is always one
        List<ENTUser> userList = tpUser.getAllTpUsers(vUserId);
        List<String> usernameList = new ArrayList<>();
        if (userList.size() > 0) {
            for (ENTUser curUser : userList) {
                usernameList.add(curUser.getUsername());
                break;
            }
            return usernameList;
        } else {
            return Collections.<String>emptyList();
        }
    }
}
