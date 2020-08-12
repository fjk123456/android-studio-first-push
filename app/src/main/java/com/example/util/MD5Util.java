package com.example.util;

import android.os.Message;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    public static String strToMd5(String stri){
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte [] bytes = digest.digest(stri.getBytes());
            StringBuilder buffer = new StringBuilder();
            for(byte b : bytes){
                int m  = b & 0xff;
                String str = Integer.toHexString(m);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}
