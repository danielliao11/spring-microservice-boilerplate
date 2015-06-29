package com.github.saintdan.tools;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Random;

/**
 * Get random string.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/28/15
 * @since JDK1.8
 */
public class RandomUtil extends StringUtils {


    private static final char[] CHARS = {'0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z'};

    /**
     * Generates a (0 to 9 and the combination of A ~ Z) String, the string
     * length is specified by the len
     *
     * @param len length
     * @return a length is len randomCode String
     */
    public static String generateRandomCode(int len) {
        if (len < 1) {
            len = 4;
        }
        return RandomStringUtils.random(len, 0, 36, true, true, CHARS,
                new Random(System.currentTimeMillis()));
    }
}
