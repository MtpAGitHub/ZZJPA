/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mtpa.jpa.iface;

import com.mtpa.jpa.enums.CurrencyEnum;

/**
 *
 * @author MtpA
 * 090414   Added the thrown exception
 */
public interface ConvertCurrencyLocal {

    double ConvertCurrency(double vAmount, CurrencyEnum vCurrencyFrom, CurrencyEnum vCurrencyTo) throws Exception;
    
}
