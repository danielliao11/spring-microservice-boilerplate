package com.saintdan.framework.param;

import com.saintdan.framework.annotation.SignField;
import com.saintdan.framework.constant.SignatureConstant;
import com.saintdan.framework.enums.ErrorType;
import com.saintdan.framework.exception.CommonsException;
import com.saintdan.framework.tools.SignatureUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Base param.
 * <pre>
 *
 * </pre>
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 8/19/15
 * @since JDK1.8
 */
public class BaseParam implements Serializable {

    private static final Set<String> baseFields = new HashSet<>();

    static {
        baseFields.add("sign");
        baseFields.add("currentUser");
    }

    private static final String COMMA = ",";

    private static final String EQUAL = "=";

    private static final long serialVersionUID = -103658650614029839L;

    @NotNull(message = "sign could not be null.")
    private String sign;

    private UserDetails currentUser;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public UserDetails getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserDetails currentUser) {
        this.currentUser = currentUser;
    }

    public boolean isSignValid(String publicKey) throws CommonsException {
        String content = getSignContent();
        return SignatureUtils.rsaCheckContent(content, this.getSign(), publicKey, SignatureConstant.CHARSET_UTF8);
    }

    /**
     * Signature.
     *
     * @param privateKey    Local private key.
     * @throws CommonsException
     */
    public void sign(String privateKey) throws CommonsException {
        String content = getSignContent();//JsonConverter.convertToJSON(this).toString();
        this.sign = SignatureUtils.rsaSign(content, privateKey, SignatureConstant.CHARSET_UTF8);
    }

    /**
     * Get the signature content.
     *
     * @return              signature content
     * @throws CommonsException
     */
    public String getSignContent() throws CommonsException {
        StringBuffer buffer = new StringBuffer();
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
                if (method == null) { // Ignore read-only field
                    continue;
                }
                Field field = null;
                String itemName = pd.getName();
                try{
                    if (baseFields.contains(itemName)) {
                        field = BaseParam.class.getDeclaredField(itemName);
                    } else {
                        field = this.getClass().getDeclaredField(itemName);
                    }
                }catch(Exception ignored){

                }

                if (field == null || !field.isAnnotationPresent(SignField.class)) {
                    continue; // Ignore field without ParamField annotation.
                }
                field.setAccessible(true);
                Object itemValue = field.get(this);
                if (itemValue == null) {
                    continue;
                }
                buffer.append(itemName).append(EQUAL);
                if (itemValue.getClass().isAssignableFrom(List.class)){
                    List<?> list = (List<?>)itemValue;
                    for (Object object : list) {
                        buffer.append(object);
                    }
                }else{
                    buffer.append(itemValue);
                }
            }
            return buffer.toString();
        } catch (Exception e) {
            throw new CommonsException(ErrorType.UNKNOWN);
        }
    }

}
