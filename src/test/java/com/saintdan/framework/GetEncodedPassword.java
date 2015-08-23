package com.saintdan.framework;

import com.saintdan.framework.tools.CustomPasswordEncoder;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/29/15
 * @since JDK1.8
 */
public class GetEncodedPassword {

    private static final String RAW_PASSWORD = "root";

    public static void main(String[] args) {
        String encodedPassword = new CustomPasswordEncoder().encode(RAW_PASSWORD);

        System.out.println(encodedPassword);
    }
}
