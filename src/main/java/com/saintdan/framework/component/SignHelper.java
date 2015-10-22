package com.saintdan.framework.component;

import com.saintdan.framework.constant.SignatureConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.exception.SignatureException;
import com.saintdan.framework.param.BaseParam;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Sign helper.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 9/22/15
 * @since JDK1.8
 */
@Component
public class SignHelper {

    public boolean signCheck(String publicKey, BaseParam params, String sign)
            throws UnsupportedEncodingException, SignatureException {
        // Prepare to validate signature.
        if (StringUtils.isEmpty(sign)) {
            throw new SignatureException(ErrorType.SYS0002);
        }
        // Transform encode.
        params.setSign(URLDecoder.decode(new String(Base64.decodeBase64(sign.getBytes())), SignatureConstant.CHARSET_UTF8));
        // Signature
        return params.isSignValid(publicKey);
    }
}
