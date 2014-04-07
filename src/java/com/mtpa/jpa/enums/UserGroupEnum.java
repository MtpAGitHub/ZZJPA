/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mtpa.jpa.enums;

/**
 *
 * @author MtpA
 * 060414   Groups that are available to users
 */
public enum UserGroupEnum {
    USER("users"),
    ADMIN("admins");
    
    private String userGroup;
    
    private UserGroupEnum(String vUserRole) {
        this.userGroup = vUserRole;
    }

    public String getUserGroup() {
        return userGroup;
    }
    
}
