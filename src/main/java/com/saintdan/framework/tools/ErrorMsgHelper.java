package com.saintdan.framework.tools;

import com.saintdan.framework.enums.ErrorType;

/**
 * Format the error msg.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 11/6/15
 * @since JDK1.8
 */
public class ErrorMsgHelper {

    /**
     * Get return error message.
     *
     * @param msg           error type
     * @param args          args
     * @return              return error message
     */
    public static String getReturnMsg(ErrorType msg, String... args) {
        final String COLON = ": ";
        StringBuffer stringBuffer = new StringBuffer();
        if (args != null) {
            stringBuffer
                    .append(msg.name())
                    .append(COLON)
                    .append(String.format(msg.description(), args));
        } else {
            stringBuffer.append(msg.name()).append(COLON).append(msg.description());
        }
        return stringBuffer.toString();
    }
}
