/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mtpa.jpa.enums;

/**
 *
 * @author MtpA
 * 140314   Created to specify which permitted currencies are available
 */
public enum CurrencyEnum {
    GBP("GBP"),
    EUR("EUR"),
    USD("USD");
    
    private String validCurrency;
    
    private CurrencyEnum(String vValidCurrency) {
        this.validCurrency = vValidCurrency;
    }

    public String getValidCurrency() {
        return validCurrency;
    }
    
}
