//250314    MtpA    Created

package com.mtpa.jpa.ejb;

import com.mtpa.jpa.iface.DateStampLocal;
import com.mtpa.wsassign.WSDateStamp_Service;
import java.util.Date;
import javax.ejb.Stateless;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.WebServiceRef;

@Stateless
public class DateStampBean implements DateStampLocal {
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/WSDateStamp/WSDateStamp.wsdl")
    private WSDateStamp_Service service;

    private Date wsDateStamp;
    
    public DateStampBean() {        
    }

    @Override
    public Date getWsDateStamp() {
        wsDateStamp = getDateStamp().toGregorianCalendar().getTime();
        return wsDateStamp;
    }

    @Override
    public void setWsDateStamp(Date wsDateStamp) {
        this.wsDateStamp = wsDateStamp;
    }

    private XMLGregorianCalendar getDateStamp() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        com.mtpa.wsassign.WSDateStamp port = service.getWSDateStampPort();
        return port.getDateStamp();
    }

    private void setDateStamp(javax.xml.datatype.XMLGregorianCalendar arg0) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        com.mtpa.wsassign.WSDateStamp port = service.getWSDateStampPort();
        port.setDateStamp(arg0);
    }

}
