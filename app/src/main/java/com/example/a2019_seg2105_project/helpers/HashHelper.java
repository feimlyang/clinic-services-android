package com.example.a2019_seg2105_project.helpers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class HashHelper {

    private static String buildString(byte[] hashBytes)
    {
        StringBuffer resultInHexFormat = new StringBuffer();
        for (int i = 0; i < hashBytes.length; i++) {
            String hexValue = Integer.toHexString(0xff & hashBytes[i]);
            if(hexValue.length() == 1)  // e.g. 0a, 0b, 0c make sure it is a 128-bit result
            {
                resultInHexFormat.append('0');
            }
            resultInHexFormat.append(hexValue);
        }
        return resultInHexFormat.toString();

    }
    public static String hash(String origin)
    {
        String result;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBtyes = digest.digest(origin.getBytes(StandardCharsets.UTF_8));
            result = buildString(hashBtyes);
        }catch(Exception e){
            result = null;
        }
        return result;
    }
}
