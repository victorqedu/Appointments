package com.caido.appointments.Util;

import java.util.regex.Pattern;

public class Functions {
    public static boolean empty(String s) {
        if(s==null) {
            return true;
        }
        return !(s!=null & !s.equals(""));
    }    

    public static boolean checkEmail(String emailAddress) {
        return Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
            .matcher(emailAddress)
            .matches();
    }

}
