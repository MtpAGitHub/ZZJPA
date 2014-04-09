/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mtpa.jpa.ejb;

/**
 *
 * @author MtpA
 * 090414   Error picked up during testing.  Now throw all exceptions rather than catch as nothing can be done at this point to recover
 * 310314   Created taking code from an existing test project for WS and RESTFul
 */
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

    //standard XML parsing.  Build the DOM and then traverse the tree looking for the element CurrencyConversion
    // then get the attribute ConvertedAmount before extracting the value it holds
    @Override
    public double getConversion(String convData) throws Exception {
        String baseCurrency="-1";
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
    }    
}
