package com.saintdan.framework.bo;

import com.saintdan.framework.annotation.ParamField;
import com.saintdan.framework.constant.SignatureConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.exception.SignatureException;
import com.saintdan.framework.tools.SignatureUtils;

import javax.validation.constraints.NotNull;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Base param.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 8/19/15
 * @since JDK1.8
 */
public class BaseParams {

    private static final Set<String> baseFields = new HashSet<>();

    @NotNull(message="sign could not be null.")
    private String sign;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    /**
     * Validate the signature.
     *
     * @param publicKey     Opposite end public key
     * @return              true/false
     * @throws SignatureException
     */
    public boolean isSignValid(String publicKey) throws SignatureException {
        String content = getSignContent();
        return SignatureUtils.rsaCheckContent(content, this.getSign(), publicKey, SignatureConstant.CHARSET_UTF8);
    }

    /**
     * Signature.
     *
     * @param privateKey    Local private key.
     * @throws SignatureException
     */
    public void sign(String privateKey) throws SignatureException {
        String content = getSignContent();//JsonConverter.convertToJSON(this).toString();
        this.sign = SignatureUtils.rsaSign(content, privateKey, SignatureConstant.CHARSET_UTF8);
    }

    /**
     * Get the signature string.
     *
     * @return              signature string
     * @throws SignatureException
     */
    public String getSignContent() throws SignatureException {
        StringBuffer sb = new StringBuffer();
        try{
            BeanInfo beanInfo = Introspector.getBeanInfo(this.getClass());
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            Arrays.sort(pds, new Comparator<PropertyDescriptor>() {
                public int compare(PropertyDescriptor o1, PropertyDescriptor o2) {
                    return (o1.getName().compareTo(o2.getName()));
                }
            });
            for (PropertyDescriptor pd : pds) {
                Method method = pd.getReadMethod();
                if (method == null) { // ignore read-only fields
                    continue;
                }
                Field field = null;
                String itemName = pd.getName();
                try{
                    if (baseFields.contains(itemName)) {
                        field = BaseParams.class.getDeclaredField(itemName);
                    } else {
                        field = this.getClass().getDeclaredField(itemName);
                    }
                }catch(Exception ignored){

                }

                if (field == null || !field.isAnnotationPresent(ParamField.class)) continue;
                field.setAccessible(true);
                Object itemValue = field.get(this);
                if (itemValue == null) continue;
                sb.append(itemName).append("=");
                if (itemValue.getClass().isAssignableFrom(List.class)){
                    List<?> list = (List<?>)itemValue;
                    for (Object object : list) {
                        sb.append(object);
                    }
                }else{
                    sb.append(itemValue);
                }
            }

            return sb.toString();
        } catch (Exception e) {
            throw new SignatureException(ErrorType.UNKNOWN);
        }

    }
}
