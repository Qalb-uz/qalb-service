package org.monstis.group.qalbms.dto;


import java.io.Serializable;


public class Data implements Serializable {
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;
}
