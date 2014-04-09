/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mtpa.jpa.iface;

/**
 *
 * @author MtpA
 * 090414   Added thr exception throw to correspond to method in class
 */
public interface RESTfulXMLExtractLocal {

    double getConversion(String convData) throws Exception;
    
}
