//230214    -   MtpA    Create class

package com.mtpa.jpa.ejb;

import com.mtpa.jpa.enums.ReqAcceptEnum;
import com.mtpa.jpa.enums.ReqTypeEnum;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
 
public class RESTfulClientBean {
    
    private String restUrl;
    private ReqAcceptEnum restAccept;
    private ReqTypeEnum restReq;
    private String urlParms;
    
    public RESTfulClientBean() {
        
    }

    public RESTfulClientBean(String vRESTurl, ReqAcceptEnum vAcceptFormat, ReqTypeEnum vRequestType, String vUrlParms) {
        this.restUrl = vRESTurl;
        this.restAccept = vAcceptFormat;
        this.restReq = vRequestType;
        this.urlParms = vUrlParms;
    }
    
    public String getRestUrl() {
        return restUrl;
    }

    public void setRestUrl(String restUrl) {
        this.restUrl = restUrl;
    }

    public ReqAcceptEnum getRestAccept() {
        return restAccept;
    }

    public void setRestAccept(ReqAcceptEnum restAccept) {
        this.restAccept = restAccept;
    }

    public ReqTypeEnum getRestReq() {
        return restReq;
    }

    public void setRestReq(ReqTypeEnum restReq) {
        this.restReq = restReq;
    }

    public String getUrlParms() {
        return urlParms;
    }

    public void setUrlParms(String urlParms) {
        this.urlParms = urlParms;
    }
    
    
    public String restfulRequest() throws Exception {
        URL reqURL = new URL(restUrl);
        HttpURLConnection restConnection = (HttpURLConnection) reqURL.openConnection();
        
        //add request header
        restConnection.setRequestMethod(restReq.getReqType());
        restConnection.setRequestProperty("Accept", restAccept.getReqAccept());

        //optional body
        if (!urlParms.equals("")) {
            restConnection.setDoOutput(true);
            DataOutputStream reqBody = new DataOutputStream(restConnection.getOutputStream());
            reqBody.writeBytes(urlParms);
            reqBody.flush();
            reqBody.close();
        }
        
        // Send request
        int responseCode = restConnection.getResponseCode();
        System.out.println("\nSending " + restReq.getReqType() + " request to URL : " + reqURL);
        System.out.println("Post parameters : " + urlParms);
        System.out.println("Response Code : " + responseCode);
        
        if (responseCode == 200) {
            BufferedReader restResponse = new BufferedReader(new InputStreamReader(restConnection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = restResponse.readLine()) != null) {
                response.append(inputLine);
            }
            restResponse.close();
            System.out.println(response.toString());
            return inputLine;
        } else {
            return null;            
        }
    }    
}
