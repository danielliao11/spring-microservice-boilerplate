package com.saintdan.framework.component;

import com.saintdan.framework.bo.BaseBO;
import com.saintdan.framework.constant.ResultConstant;
import com.saintdan.framework.constant.SignatureConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.exception.SignatureException;
import com.saintdan.framework.vo.ResultVO;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ResultHelper resultHelper;

    public ResultVO signCheck(String publicKey, BaseBO params, String sign)
            throws UnsupportedEncodingException, SignatureException {
        // Prepare to validate signature.
        if (StringUtils.isEmpty(sign)) {
            return resultHelper.infoResp(ErrorType.SYS0002, "Sign cannot be null.");
        }
        // Transform encode.
        params.setSign(URLDecoder.decode(new String(Base64.decodeBase64(sign.getBytes())), SignatureConstant.CHARSET_UTF8));
        // Signature
        if (!params.isSignValid(publicKey)) {
            return resultHelper.infoResp(ErrorType.SGN0021);
        }
        // return OK
        return new ResultVO(ResultConstant.OK);
    }
}
