package com.saintdan.framework.common.component;

import com.saintdan.framework.common.constant.SignatureConstant;
import com.saintdan.framework.common.exception.IllegalParamException;
import com.saintdan.framework.common.exception.SignFailedException;
import com.saintdan.framework.common.param.BaseParam;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
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
  public boolean signCheck(String publicKey, BaseParam params) throws UnsupportedEncodingException, SignFailedException, IllegalParamException {
    // Prepare to validateWithOutSignCheck signature.
    if (StringUtils.isEmpty(params.getSign())) {
      throw new IllegalParamException("sign cannot be null");
    }
    // Transform encode.
    params.setSign(URLDecoder.decode(new String(Base64.decodeBase64(params.getSign())), SignatureConstant.CHARSET_UTF8));
    // Signature
    return params.isSignValid(publicKey);
  }
}
