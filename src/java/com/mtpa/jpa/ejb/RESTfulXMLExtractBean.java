//310314    MtpA    Created (taken from my existing test project for WS and RESTful)

package com.mtpa.jpa.ejb;

import com.mtpa.jpa.iface.RESTfulXMLExtractLocal;
import java.io.IOException;
import java.io.StringReader;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@Stateless
@Local(RESTfulXMLExtractLocal.class)
public class RESTfulXMLExtractBean implements RESTfulXMLExtractLocal {

    public RESTfulXMLExtractBean() {
        
    }

    @Override
    public double getConversion(String convData) {
        String baseCurrency="-1";
        try {
            Document xmlDomDoc;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            xmlDomDoc = db.parse(new InputSource(new StringReader(convData)));
            NodeList baseNodes = xmlDomDoc.getElementsByTagName("CurrencyConversion");
            for (int i = 0; i < baseNodes.getLength(); i++) {
                Node baseNode = baseNodes.item(i);
                baseCurrency = baseNode.getAttributes().getNamedItem("ConvertedAmount").getNodeValue();
            }
            return Double.parseDouble(baseCurrency);
        } catch (ParserConfigurationException | SAXException | IOException | NumberFormatException e) {
            return -1;
        }
    }    
}
