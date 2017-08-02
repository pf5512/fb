package com.footballer.util;

import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ian on 6/14/14.
 */
public class SecurityUtils {

    public static String md5(String input) {
        if (!StringUtils.hasText(input))
            throw new IllegalArgumentException();

        String md5 = null;

        try {
            //Create MessageDigest object for MD5
            MessageDigest digest = MessageDigest.getInstance("MD5");

            //Update input string in message digest
            digest.update(input.getBytes(), 0, input.length());

            //Converts message digest value in base 16 (hex)
            md5 = new BigInteger(1, digest.digest()).toString(16);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return md5;
    }

    public static void main(String[] args) {
        System.out.println(md5("1111"));
        System.out.println(md5("ian"));
    }
}
