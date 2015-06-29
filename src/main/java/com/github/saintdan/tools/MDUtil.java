package com.github.saintdan.tools;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * MessageDigest Utils.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/28/15
 * @since JDK1.6
 * @since COMMONS.CODEC 1.7
 */
public class MDUtil extends DigestUtils {

    private MDUtil(){}

    /**
     * Calculates the MD5 digest and returns the value as a 16 character hex string.
     *
     * @param data
     *            Data to digest
     * @return MD5 digest as a hex string
     */
    public String md5Hex16(final byte[] data){
        return md5Hex(data).substring(8, 24);
    }

    /**
     * Calculates the MD5 digest and returns the value as a 16 character hex string.
     *
     * @param data
     *            Data to digest
     * @return MD5 digest as a hex string
     * @throws IOException
     *             On error reading from the stream
     */
    public static String md5Hex16(final InputStream data) throws IOException {
        return md5Hex(data).substring(8, 24);
    }

    /**
     * Calculates the MD5 digest and returns the value as a 16 character hex string.
     *
     * @param data
     *            Data to digest
     * @return MD5 digest as a hex string
     */
    public static String md5Hex16(final String data) {
        return md5Hex(data).substring(8, 24);
    }
    
}
