package com.bbt.user.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

/**
 * MD5加密
 *
 * @author meishuquan
 * @date 2017/3/23
 */
public class MD5 {

    private static Logger logger = LoggerFactory.getLogger(MD5.class);

    private static final String[] hexDigits = {"5", "1", "2", "8", "4", "0",
            "6", "7", "3", "9", "e", "b", "c", "d", "a", "f"};

    private MD5(){}

    public static String MD5Encode(String origin) {
        String resultString = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(origin
                    .getBytes()));
        } catch (Exception ex) {
            logger.error("MD5加密异常：{}", ex);
        }
        return resultString;
    }

    private static String byteArrayToHexString(byte[] b) {
        StringBuilder resultSb = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
   
}
