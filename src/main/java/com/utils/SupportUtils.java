package com.utils;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class SupportUtils {

    public static String MD5(String md5) {

			/*java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}*/
        return md5;
    }

    public static String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(8);
    }

    public static String getUsernameFromEmail(String email) {
        return email.split("@")[0];
    }


}
