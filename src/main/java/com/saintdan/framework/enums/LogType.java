package com.saintdan.framework.enums;

/**
 * Operation type in log.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/27/15
 * @since JDK1.8
 */
public enum LogType {

    LOGIN(0),
    CREATE(1),
    READ(2),
    UPDATE(3),
    DELETE(4);

    private int code;

    LogType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
