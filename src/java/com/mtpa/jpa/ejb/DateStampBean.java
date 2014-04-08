//250314    MtpA    Created

package com.mtpa.jpa.ejb;

import com.mtpa.datestamp.DateStampWS_Service;
import com.mtpa.jpa.iface.DateStampLocal;
import java.util.Date;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.WebServiceRef;

@Stateless
@Local(DateStampLocal.class)
public class DateStampBean implements DateStampLocal {
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/DateStampWS/DateStampWS.wsdl")
    private DateStampWS_Service service_2;

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
        com.mtpa.datestamp.DateStampWS port = service_2.getDateStampWSPort();
        return port.getDateStamp();
    }

    private void setDateStamp(javax.xml.datatype.XMLGregorianCalendar arg0) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        com.mtpa.datestamp.DateStampWS port = service_2.getDateStampWSPort();
        port.setDateStamp(arg0);
    }

}
