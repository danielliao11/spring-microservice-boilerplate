package com.saintdan.framework.tools;

import com.saintdan.framework.constant.SignatureConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.exception.CommonsException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Signature utilities.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 8/19/15
 * @since JDK1.8
 */
public class SignatureUtils {

  /**
   * Check the sign content.
   *
   * @param content   src content
   * @param sign      signature content
   * @param publicKey opposite end public key
   * @param charset   charset
   * @return true/false
   */
  public static boolean rsaCheckContent(String content, String sign, String publicKey,
      String charset)
      throws CommonsException {
    try {
      PublicKey pubKey = getPublicKeyFromX509(SignatureConstant.SIGN_TYPE_RSA,
          new ByteArrayInputStream(publicKey
              .getBytes()));

      java.security.Signature signature = java.security.Signature
          .getInstance(SignatureConstant.SIGN_ALGORITHMS);

      signature.initVerify(pubKey);

      if (StringUtils.isEmpty(charset)) {
        signature.update(content.getBytes());
      } else {
        signature.update(content.getBytes(charset));
      }

      return signature.verify(Base64.decodeBase64(sign.getBytes()));
    } catch (Exception e) {
      throw new CommonsException(ErrorType.SYS0004);
    }
  }

  /**
   * Signature by local private key.
   *
   * @param content    src
   * @param privateKey local private key
   * @param charset    charset
   * @return signature
   */
  public static String rsaSign(String content, String privateKey, String charset)
      throws CommonsException {
    try {
      PrivateKey priKey = getPrivateKeyFromPKCS8(SignatureConstant.SIGN_TYPE_RSA,
          new ByteArrayInputStream(privateKey.getBytes()));

      java.security.Signature signature = java.security.Signature
          .getInstance(SignatureConstant.SIGN_ALGORITHMS);

      signature.initSign(priKey);

      if (StringUtils.isEmpty(charset)) {
        signature.update(content.getBytes());
      } else {
        signature.update(content.getBytes(charset));
      }

      byte[] signed = signature.sign();

      return new String(Base64.encodeBase64(signed));
    } catch (Exception e) {
      throw new CommonsException(ErrorType.SYS0004);
    }
  }

  public static PublicKey getPublicKeyFromX509(String algorithm, InputStream ins)
      throws Exception {
    KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

    StringWriter writer = new StringWriter();
    StreamUtils.io(new InputStreamReader(ins), writer);

    byte[] encodedKey = writer.toString().getBytes();

    encodedKey = Base64.decodeBase64(encodedKey);

    return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
  }

  public static PrivateKey getPrivateKeyFromPKCS8(String algorithm, InputStream ins)
      throws Exception {
    if (ins == null || StringUtils.isEmpty(algorithm)) {
      return null;
    }

    KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

    byte[] encodedKey = StreamUtils.readText(ins).getBytes();

    encodedKey = Base64.decodeBase64(encodedKey);

    return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
  }
}
