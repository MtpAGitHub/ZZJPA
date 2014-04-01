/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mtpa.jpa.iface;

import com.mtpa.jpa.entity.ENTRequest;
import com.mtpa.jpa.enums.RequestStatusEnum;
import java.util.Date;
import java.util.List;

/**
 *
 * @author MtpA
 */
public interface RequestJPALocal {

    void createRequest(long vRequestorId, long vRequesteeId, double vAmount, long vRequesteeAccId,  Date vCreateDate);

    List<ENTRequest> getPendingList(RequestStatusEnum vRequestStatus);
            
    List<ENTRequest> getRequestList();
    
}
