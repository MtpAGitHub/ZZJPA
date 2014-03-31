//230214    -   MtpA    Create class

package com.mtpa.jpa.ejb;

import com.mtpa.jpa.iface.RESTfulClientLocal;
import com.mtpa.jpa.enums.ReqAcceptEnum;
import com.mtpa.jpa.enums.ReqTypeEnum;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.ejb.Stateless;
 
@Stateless
public class RESTfulClientBean implements RESTfulClientLocal {
    
    private String restUrl;
    private ReqAcceptEnum restAccept;
    private ReqTypeEnum restReq;
    private String urlParms;
    
    public RESTfulClientBean() {
        
    }

    @Override
    public String getRestUrl() {
        return restUrl;
    }

    @Override
    public void setRestUrl(String restUrl) {
        this.restUrl = restUrl;
    }

    @Override
    public ReqAcceptEnum getRestAccept() {
        return restAccept;
    }

    @Override
    public void setRestAccept(ReqAcceptEnum restAccept) {
        this.restAccept = restAccept;
    }

    @Override
    public ReqTypeEnum getRestReq() {
        return restReq;
    }

    @Override
    public void setRestReq(ReqTypeEnum restReq) {
        this.restReq = restReq;
    }

    @Override
    public String getUrlParms() {
        return urlParms;
    }

    @Override
    public void setUrlParms(String urlParms) {
        this.urlParms = urlParms;
    }
    
    
    @Override
    public String restfulRequest(String vRESTurl, ReqAcceptEnum vAcceptFormat, ReqTypeEnum vRequestType, String vUrlParms) throws Exception {
        URL reqURL = new URL(vRESTurl);
        HttpURLConnection restConnection = (HttpURLConnection) reqURL.openConnection();
        
        //add request header
        restConnection.setRequestMethod(vRequestType.getReqType());
        restConnection.setRequestProperty("Accept", vAcceptFormat.getReqAccept());

        //optional body
        if (!vUrlParms.equals("")) {
            restConnection.setDoOutput(true);
            DataOutputStream reqBody = new DataOutputStream(restConnection.getOutputStream());
            reqBody.writeBytes(vUrlParms);
            reqBody.flush();
            reqBody.close();                
        }
        
        // Send request
        int responseCode = restConnection.getResponseCode();
        System.out.println("\nSending " + vRequestType.getReqType() + " request " + vAcceptFormat.getReqAccept() + "accept format to URL : " + reqURL);
        System.out.println("Post parameters : " + vUrlParms);
        System.out.println("Response Code : " + responseCode);
        
        if (responseCode == 200) {
            String inputLine;
            StringBuilder response;
            BufferedReader restResponse = new BufferedReader(new InputStreamReader(restConnection.getInputStream()));
            response = new StringBuilder();
            while ((inputLine = restResponse.readLine()) != null) {
                response.append(inputLine);
            }
            System.out.println(response.toString());
            return response.toString();
        } else {
            return null;            
        }
    }    
}
