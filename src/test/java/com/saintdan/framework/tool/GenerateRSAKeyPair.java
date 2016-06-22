package com.saintdan.framework.tool;

import org.apache.commons.codec.binary.Base64;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;

/**
 * You can use this to generate your own key pair.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 8/19/15
 * @since JDK1.8
 */
public class GenerateRSAKeyPair {

  private HashMap<String, Object> getKeys() throws NoSuchAlgorithmException {
    HashMap<String, Object> map = new HashMap<>();
    KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
    keyPairGen.initialize(2048);
    KeyPair keyPair = keyPairGen.generateKeyPair();
    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
    RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
    map.put("public", publicKey);
    map.put("private", privateKey);
    return map;
  }

  public static void main(String[] args) {
    HashMap<String, Object> rsaMap = null;
    try {
      rsaMap = new GenerateRSAKeyPair().getKeys();
    } catch (NoSuchAlgorithmException e) {
      System.out.println(e);
    }
    RSAPublicKey publicKey = (RSAPublicKey) rsaMap.get("public");
    String publicKeyStr = new String(Base64.encodeBase64(publicKey.getEncoded()));
    System.out.println("Public key is: " + publicKeyStr);

    RSAPrivateKey privateKey = (RSAPrivateKey) rsaMap.get("private");
    String privateKeyStr = new String(Base64.encodeBase64(privateKey.getEncoded()));
    System.out.println("Private Key is: " + privateKeyStr);
  }
}
