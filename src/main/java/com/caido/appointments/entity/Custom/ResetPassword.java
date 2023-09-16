package com.caido.appointments.entity.Custom;

public class ResetPassword {
    private String jwtToken;
    private String password;

    public ResetPassword() {
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public String getPassword() {
        return password;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    

    
}
