package com.caido.appointments.Util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CNPStuff {
    public static boolean checkCNP(String cnp) {
        if(cnp.equals("0000000000000")) {
            return true;
        }
        Integer[] keyTest = new Integer[12];
        keyTest[0] = 2;
        keyTest[1] = 7;
        keyTest[2] = 9;
        keyTest[3] = 1;
        keyTest[4] = 4;
        keyTest[5] = 6;
        keyTest[6] = 3;
        keyTest[7] = 5;
        keyTest[8] = 8;
        keyTest[9] = 2;
        keyTest[10] = 7;
        keyTest[11] = 9;
        Pattern p = Pattern.compile("^\\d{13}$");
        Matcher m = p.matcher (cnp);
        if(!m.find ()) {
            throw new RuntimeException("CNP-ul trebuie sa conțină doar 13 cifre "+cnp);
        }

        Integer month = getMonth(cnp);
        if(month<1 || month>12) {
            throw new RuntimeException("Luna trebuie sa se incadreze in intervalul 1-12 "+month);
        } 

        Integer day = getRawDay(cnp);
        if(day<1 || day>31) {
            throw new RuntimeException("Ziua trebuie sa se incdrese in intervalul 1-31 "+day);
        }
        Integer sumaControl = 0;
        for (int i = 0;i < cnp.length()-1; i++){
            char ch = cnp.charAt(i);
            Integer intVal = Integer.valueOf(""+ch);
            //System.err.println("VAL "+intVal);
            if(i==0 && intVal==0) {
                throw new RuntimeException("Prima cifra a cnp-ului nu poate fi 0");
            } 
            sumaControl+=intVal*keyTest[i];
        }
        
        Integer rest = sumaControl % 11;
        if(rest==10){ rest = 1; }
        
        Integer cn = getControlNumber(cnp);
        if(Objects.equals(rest, cn)) {
            return true;
        } else {
            throw new RuntimeException("Cifra de control nu este corespunzatoare "+rest+" != "+cn);
        }
    }
    
    public static LocalDate getBirthDate(String cnp) {
        Integer year = getYear(cnp);
        Integer month = getMonth(cnp);
        Integer day = getRawDay(cnp);
        String extraMonthZero = "";
        if(month<10){
            extraMonthZero = "0";
        }
        String extraDayZero = "";
        if(day<10){
            extraDayZero = "0";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(year+"-"+extraMonthZero+month+"-"+extraDayZero+day, formatter);
    }

    public String getBirthDateEnclosed(String cnp) {
        return "'"+getBirthDate(cnp)+"'";
    }

    public static Integer getControlNumber(String cnp) {
        return Integer.valueOf(cnp.substring(12, 13));
    }

    public static Integer getRawYear(String cnp) {
        return Integer.valueOf(cnp.substring(1, 3));
    }

    public static Integer getMonth(String cnp) {
        return Integer.valueOf(cnp.substring(3, 5));
    }

    public static Integer getRawDay(String cnp) {
        return Integer.valueOf(cnp.substring(5, 7));
    }
    
    public static Integer getYear(String cnp) {
        String rawYear = cnp.substring(1, 3);
        Integer intVal = Integer.valueOf(cnp.substring(0, 1));
        switch (intVal) {
            case 1, 2 -> {
                return Integer.valueOf("19"+rawYear);
            }
            case 3, 4 -> {
                return Integer.valueOf("18"+rawYear);
            }
            case 5, 6 -> {
                return Integer.valueOf("20"+rawYear);
            }
            case 7, 8, 9 -> {
                return Integer.valueOf("19"+rawYear);
            }
            default -> {
            }
        }
        return null;
    }
    
    public static Integer getSex(String cnp) {
        Integer intVal = Integer.valueOf(cnp.substring(0, 1));
        return switch (intVal) {
            case 1, 3, 5, 7 -> 1;
            case 2, 4, 6, 8 -> 2;
            default -> 4;
        }; 
    }

    public String getSexCode(String cnp) {
        Integer intVal = Integer.valueOf(cnp.substring(0, 1));
        return switch (intVal) {
            case 1, 3, 5, 7 -> "Masculin";
            case 2, 4, 6, 8 -> "Feminin";
            default -> "Necunoscut";
        }; 
    }
}
