/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//230214    -   MtpA    Create class

package com.mtpa.jpa.ejb;

/**
 *
 * @author MtpA
 * 090414   Added exception throw if you do not get a 200 OK from the RESTFul service.  Can't handle here so pass up
 * 230314   Created class based on previous version I had used in earlier project (hence the 'general nature' of the code)
 */
import com.mtpa.jpa.iface.RESTfulClientLocal;
import com.mtpa.jpa.enums.ReqAcceptEnum;
import com.mtpa.jpa.enums.ReqTypeEnum;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.ejb.Local;
import javax.ejb.Stateless;
 
@Stateless
@Local(RESTfulClientLocal.class)
public class RESTfulClientBean implements RESTfulClientLocal {
    
    private String restUrl;
    private ReqAcceptEnum restAccept;
    private ReqTypeEnum restReq;
    private String urlParms;
    
    public RESTfulClientBean() {
        
    }

    //standard getters & setters
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
    

    //set up the RESTFul client with args passed into method to set the correct message type and response required (in this instance always XML)
    @Override
    public String restfulRequest(String vRESTurl, ReqAcceptEnum vAcceptFormat, ReqTypeEnum vRequestType, String vUrlParms) throws Exception {
        //set up the Url and create a new restful connection
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
        
        //only carry on if you have got an OK status back from the RESTFul service.  Otherwise throw an exception and handle at from end
        //standard string building loop to read the input stream and convert into a String which we pass back
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
            throw new Exception();            
        }
    }    
}
