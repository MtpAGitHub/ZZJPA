/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mtpa.jpa.iface;

import com.mtpa.jpa.enums.ReqAcceptEnum;
import com.mtpa.jpa.enums.ReqTypeEnum;

/**
 *
 * @author MtpA
 */
public interface RESTfulClientLocal {

    ReqAcceptEnum getRestAccept();

    ReqTypeEnum getRestReq();

    String getRestUrl();

    String getUrlParms();

    String restfulRequest(String vRESTurl, ReqAcceptEnum vAcceptFormat, ReqTypeEnum vRequestType, String vUrlParms) throws Exception;

    void setRestAccept(ReqAcceptEnum restAccept);

    void setRestReq(ReqTypeEnum restReq);

    void setRestUrl(String restUrl);

    void setUrlParms(String urlParms);
    
}
