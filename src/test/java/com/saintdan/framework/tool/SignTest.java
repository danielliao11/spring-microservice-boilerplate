package com.saintdan.framework.tool;

import com.saintdan.framework.constant.SignatureConstant;
import com.saintdan.framework.param.UserParam;
import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Test;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Test signature.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 8/19/15
 * @since JDK1.8
 */
public class SignTest {

  @Test
  public void testSign() throws Exception {
    UserParam param = new UserParam();
    param.setId(4L);
        param.setName("jerry");
        param.setUsr("tommy");
        param.setPwd("tom54321");
    System.out.println("Sign content is: " + param.getSignContent());
    param.sign(PRIVATE_KEY);
    String sign = param.getSign();
    System.out.println("Sign is: " + sign);
    // Encode the sign.
    String encodeSign = new String(Base64.encodeBase64(URLEncoder.encode(sign, SignatureConstant.CHARSET_UTF8).getBytes()));
    System.out.println("Encode sign is: " + encodeSign);
    String decodeSign = URLDecoder.decode(new String(Base64.decodeBase64(encodeSign)), SignatureConstant.CHARSET_UTF8);
    System.out.println("Decode sign is: " + decodeSign);
    Assert.assertTrue(param.isSignValid(PUBLIC_KEY));
    Assert.assertEquals(sign, decodeSign);
  }

  @Test
  public void testOppSign() throws Exception {
    UserParam param = new UserParam(USR);
    param.setSign(SIGN);
    Assert.assertTrue(param.isSignValid(OPP_KEY));
  }

  @Test
  public void testEncodeOppSign() throws Exception {
    // Decode the encoded sign.
    String decodeSign = URLDecoder.decode(new String(Base64.decodeBase64(ENCODE_SIGN.getBytes())), SignatureConstant.CHARSET_UTF8);
    UserParam param = new UserParam(USR);
    param.setSign(decodeSign);
    Assert.assertTrue(param.isSignValid(OPP_KEY));
  }

  private static final String PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC+k7ozUaG/hh4R44UcHjUW3PAMsHj2i+uN9hTj5i9O9e/AvUB1WUmEp+n8y94wry3Mvk+vJrXWewhCfLP91V2JaG7vGoLp8T/FtazASyQFFr9BS84VoplLplF6cayy3thNCaIRaWFnB24O8Jw+vPikS6xGD4a0RN1tao7cPQ740X1uaQQZwq8cHUEcq/o2Pw9IvRm3yHZ9tktY1IRhY8sID6ieG0YvukCSkZDEC1kslNiGtPDiijv5txKOFr/YAd+ewJwxrrkShTUFKMPXxuMb4AATok1REfk2XY2BEvvBPgsfX/S8mlScLGORZY3jrrjyTbaiByINKvveawm4DOn3AgMBAAECggEBAKT1S+vV0fLszSRg0rv27a8fiG0DZ4DIthjIE2b8vKBVQrSLW7r3kSpRCbk2Ydm0GCmr5V9PW4AOOKUMOeQSBofErJksUnraxn+E9MXxhHkfKmcDjZuuhZA0ozwgHwMo3wM3vl/h3lyG0t39qNCpU3MqyTBPiiKJpTxrSsMZ2tbFRunHN1g4sCIOzm28TlBrzO+tSl1fKqwstlCAJlJ4zwMpKh7lmAPIYXlmCPfDiwqaKBJezCBtThLHKeEgJxTXbrJ1mZwvPaW3ZtZVFwQjJy4N8XM4B3q+IQRBndw7/FR1aLIcFIunOEW13ZPy1PIJ8O94BCb9+n627Gx8xFrq+hkCgYEA635aeLESPIlBQMvzQznLPkGS/ht/1XxJ+NqLZgLbwrL1Qyab2zOgfoXea1pSjFXKZx/OHG96A7Js0p2rsPjOHdydKb7uD7qRdV1foKg7OVKQVXufhGlIIQ3F0d3WbwZNPsHMvtrN6nF5DdhIfxKQMIiwCPtRTj1zcfs9WhaRhsUCgYEAzywXL6SQxAuPZL8FZ+Li+tCTfK7pCs6/Jztz7aHcxcN9rp/LT477mh74UTzzwb3FTMAzz+00vEmq0AYbVVJnWGJMQsoCUdEyzdl48CrBtSTuLFUv4zLB7zeq0TbWuCs5z5XZ59xWya5xfJaqRjL1TJRW14oUiB1G+7MOokdZmYsCgYEAonA3y8SohaVPo3iLRjXZYgotcKZbWVEwXT2yph0WpYh38WfyXhOQg3Wn5HkbM74BrXyc2bRzf5VVxzoHMj2NP7/rkN4orCTPLRvJWLJyl6nB8ZUihV1x6Yrsh0T0IwU/xyg7KPMY0ryO+ePUrgKJ/1BpzOg+Py/YMClTOo8SttkCgYARnfk2xvK8iRMfiRLnm6abl4MDNkiA78XXizm3em0wsAgPAm2ijVEd8QZCukEsN3wAo9OEGfLDFCyfVfWbEs/Q90Lu5wi9dmGwmY6sNaaVRdevE2toEZfmRMCU2+n0bVCUM3T9P4Jt0hS6eIFwSMMNSQv9djN6w6qWmspiyFh5MwKBgBUe9odIUmpCgQSny1VR0W2Okkn8KWmWVC/qksilIIMIOxT0v8d+/cs8P0k/uH4hIqyRuvuOqvR3p2myxKewczbt2bRfSCtgceVswMM8LacbkLuaIdWZtX4encyJVWgZXdzvSp011xwGCJ7t4KZ7G/VyLJwDLHRk7feROgqUUTsb";
  private static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvpO6M1Ghv4YeEeOFHB41FtzwDLB49ovrjfYU4+YvTvXvwL1AdVlJhKfp/MveMK8tzL5Prya11nsIQnyz/dVdiWhu7xqC6fE/xbWswEskBRa/QUvOFaKZS6ZRenGsst7YTQmiEWlhZwduDvCcPrz4pEusRg+GtETdbWqO3D0O+NF9bmkEGcKvHB1BHKv6Nj8PSL0Zt8h2fbZLWNSEYWPLCA+onhtGL7pAkpGQxAtZLJTYhrTw4oo7+bcSjha/2AHfnsCcMa65EoU1BSjD18bjG+AAE6JNURH5Nl2NgRL7wT4LH1/0vJpUnCxjkWWN46648k22ogciDSr73msJuAzp9wIDAQAB";
  private static final String OPP_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvpO6M1Ghv4YeEeOFHB41FtzwDLB49ovrjfYU4+YvTvXvwL1AdVlJhKfp/MveMK8tzL5Prya11nsIQnyz/dVdiWhu7xqC6fE/xbWswEskBRa/QUvOFaKZS6ZRenGsst7YTQmiEWlhZwduDvCcPrz4pEusRg+GtETdbWqO3D0O+NF9bmkEGcKvHB1BHKv6Nj8PSL0Zt8h2fbZLWNSEYWPLCA+onhtGL7pAkpGQxAtZLJTYhrTw4oo7+bcSjha/2AHfnsCcMa65EoU1BSjD18bjG+AAE6JNURH5Nl2NgRL7wT4LH1/0vJpUnCxjkWWN46648k22ogciDSr73msJuAzp9wIDAQAB";
  private static final String USR = "admin";
  private static final String SIGN = "LKcnJ3MeW5Lq9BSblnmCIBHj7GOxMQS7j3KTE8lUyIwolAs/gMMVz5kNIiL06ePLtLI/e8KYJtj/RC7rhrHdboFhuEyFYppv0HpU2v8J1aZ2arGfmcZ+PQ+tAUCMzwfoIXEWnf0mszRq1sP2n71TkZxu2+7koPPjce/Nl6EvRujfov5bxt+gdmLsFRYDHUYAM84pNuDh6ioac2nQOuqFxxRysHMrdbIKBxiaqdqUIcsUCRo08IMZmiqHVco/YsSFtQ1N0xRo64ZKbq+owYFGoOW1D9xOBw37V1F1zSeFnJdLN60P5l0JX6TkKxL73BjJtVp6oiMUdBat82H1EcszGA==";
  private static final String ENCODE_SIGN = "TEtjbkozTWVXNUxxOUJTYmxubUNJQkhqN0dPeE1RUzdqM0tURThsVXlJd29sQXMlMkZnTU1WejVrTklpTDA2ZVBMdExJJTJGZThLWUp0aiUyRlJDN3JockhkYm9GaHVFeUZZcHB2MEhwVTJ2OEoxYVoyYXJHZm1jWiUyQlBRJTJCdEFVQ016d2ZvSVhFV25mMG1zelJxMXNQMm43MVRrWnh1MiUyQjdrb1BQamNlJTJGTmw2RXZSdWpmb3Y1Ynh0JTJCZ2RtTHNGUllESFVZQU04NHBOdURoNmlvYWMyblFPdXFGeHhSeXNITXJkYklLQnhpYXFkcVVJY3NVQ1JvMDhJTVptaXFIVmNvJTJGWXNTRnRRMU4weFJvNjRaS2JxJTJCb3dZRkdvT1cxRDl4T0J3MzdWMUYxelNlRm5KZExONjBQNWwwSlg2VGtLeEw3M0JqSnRWcDZvaU1VZEJhdDgySDFFY3N6R0ElM0QlM0Q=";
}
