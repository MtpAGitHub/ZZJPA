//230314    MtpA    Created

package com.mtpa.jpa.enums;

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
