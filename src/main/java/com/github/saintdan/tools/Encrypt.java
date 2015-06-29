package com.github.saintdan.tools;

import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Encrypt util.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/28/15
 * @since JDK1.8
 */
public class Encrypt {

    /**
     * Obtain Hex salt
     *
     * @return 32 bytes salt
     */
    public static String nextSalt(){
        return RandomUtil.generateRandomCode(32).toLowerCase();
    }

    /**
     * User passwd.
     * <p> hmac(md5(hmac(salt,value)),value)</p>
     *
     * @param salt
     *              slat
     * @param value
     *              value
     * @return passwd
     */
    public static String passwd(String salt, String value){
        String a=hmacSha1Hex(salt,value);
        String key=MDUtil.md5Hex(a);
        String result=hmacSha1Hex(key,value);
        return result;
    }

    /**
     * Check password is valid or not.
     *
     * @param encPass
     *                  a pre-encoded password
     * @param rawPass
     *                  a raw password to encode and compare against the pre-encoded password
     * @param salt
     *                  a salt value.
     * @return true if valid
     */
    public static boolean passwdValid(String encPass, String rawPass,String salt){
        return encPass.equals(passwd(salt,rawPass));
    }

    /**
     * Hmac with sha256 hex.
     *
     * @param key
     *              key
     * @param value
     *              value
     * @return hmac String
     */
    public static String hmacSha256Hex(String key,String value){
        checkArguments(key, value);
        return HmacUtils.hmacSha1Hex(key, value);

    }

    /**
     * Hmac with sha1 hex.
     *
     * @param key
     *              key
     * @param value
     *              value
     * @return hmac String
     */
    public static String hmacSha1Hex(String key,String value){
        checkArguments(key, value);
        return HmacUtils.hmacSha1Hex(key, value);

    }

    /**
     * Default hmac(HmacSha1).
     *
     * @param key
     *              key
     * @param value
     *              value
     * @return hmac String
     */
    public static String hmac(String key,String value){
        return hmacSha1Hex(key, value);
    }

    /**
     * Check encrypt arguments.
     *
     * @param arg1
     *              string
     * @param arg2
     *              string
     */
    private static void checkArguments(String arg1, String arg2) {
        if (StringUtils.isBlank(arg1))
            throw new IllegalArgumentException(
                    "value must not be null or empty.");
        if (StringUtils.isBlank(arg2))
            throw new IllegalArgumentException(
                    "value must not be null or empty.");
    }
}
