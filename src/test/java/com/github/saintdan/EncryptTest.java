package com.github.saintdan;

import com.github.saintdan.tools.Encrypt;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test the
 * {@link com.github.saintdan.tools.Encrypt}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/28/15
 * @since JDK1.8
 */
public class EncryptTest {

    Encrypt encrypt = new Encrypt();

    @Test
    public void testPasswd() throws Exception {

        String salt = encrypt.nextSalt();
        String encPass = encrypt.passwd(salt, "admin");

        Assert.assertTrue(encrypt.passwdValid(encPass, "admin", salt));
    }
}

