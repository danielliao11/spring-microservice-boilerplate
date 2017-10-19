package com.saintdan.framework.component;

import com.saintdan.framework.constant.SignatureConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.exception.CommonsException;
import com.saintdan.framework.param.BaseParam;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Sign helper.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 9/22/15
 * @since JDK1.8
 */
@Component
public class SignHelper {

  /**
   * Check the sign.
   *
   * @param publicKey public key
   * @return success or not
   */
  public boolean signCheck(String publicKey, BaseParam params)
      throws UnsupportedEncodingException, CommonsException {
    // Prepare to validateWithOutSignCheck signature.
    if (StringUtils.isEmpty(params.getSign())) {
      throw new CommonsException(ErrorType.SYS0002);
    }
    // Transform encode.
    params.setSign(URLDecoder
        .decode(new String(Base64.decodeBase64(params.getSign())), SignatureConstant.CHARSET_UTF8));
    // Signature
    return params.isSignValid(publicKey);
  }
}
