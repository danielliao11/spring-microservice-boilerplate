package com.saintdan.framework.tool;

import com.saintdan.framework.component.CustomPasswordEncoder;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * Generate encode password.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/29/15
 * @since JDK1.8
 */
public class GenerateEncodedPassword {

    private static final String RAW_PASSWORD = "root";

    public static void main(String[] args) {
        String encodedPassword = new CustomPasswordEncoder().encode(RAW_PASSWORD);
        System.out.println(encodedPassword);
    }
}
