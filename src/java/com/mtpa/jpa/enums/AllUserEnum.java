/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mtpa.jpa.enums;

/**
 *
 * @author MtpA
 * 060414   Created to set admin page all users text
 */
public enum AllUserEnum {
    ALLUSER("All users");
    
    private String allUsers;
    
    private AllUserEnum(String vAllUsers) {
        this.allUsers = vAllUsers;
    }

    public String getAllUsers() {
        return allUsers;
    }
}
