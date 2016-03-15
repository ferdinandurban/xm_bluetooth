/*
 *
 * @author Ferdinand Urban
 * Copyright (C) year  Ferdinand Urban
 *
 *
 */

package cz.xmartcar.communication.exception;

public class BaseException extends Exception {

   private static final long serialVersionUID = -1230984567657658100L;

    public BaseException(String msg) {
        super(msg);
    }

    public BaseException(String msg, Throwable exception) {
        super(msg, exception);
    }

}