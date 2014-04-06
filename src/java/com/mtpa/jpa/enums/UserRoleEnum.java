/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mtpa.jpa.enums;

/**
 *
 * @author MtpA
 * 060414   Created roles
 */
public enum UserRoleEnum {
    ADMIN("admins"),
    USER("users");
    
    private String userRole;
    
    private UserRoleEnum(String vUserRole) {
        this.userRole = vUserRole;
    }

    public String getUserRole() {
        return userRole;
    }
    
}
