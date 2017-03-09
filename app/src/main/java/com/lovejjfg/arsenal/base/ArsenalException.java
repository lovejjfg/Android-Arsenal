package com.lovejjfg.arsenal.base;

/**
 * Created by Joe on 2017/1/5.
 * Email lovejjfg@gmail.com
 */

public class ArsenalException extends RuntimeException {
    int code;
    String message;

    public ArsenalException(int code, String message) {
        super("Code:" + code + ";Message:" + message);
        this.code = code;
        this.message = message;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }

}
