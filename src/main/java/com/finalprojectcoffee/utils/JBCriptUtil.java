package com.finalprojectcoffee.utils;

import org.mindrot.jbcrypt.BCrypt;

public class JBCriptUtil {
    private static final String passwordPattern = "^(?=.[a-z])(?=.[A-Z])(?=.\\d)(?=.[@$!%?&])[A-Za-z\\d@$!%?&]{8,}$";

    public  static boolean validatePassword(String password){
        return password.matches(passwordPattern);
    }
    public static String getHashedPw(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static Boolean checkPw(String hashedPw, String password){
        if(BCrypt.checkpw(password,hashedPw)){
            return true;
        } else {
            return false;
        }
    }
}
