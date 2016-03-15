/*
 *
 * @author Ferdinand Urban
 * Copyright (C) year  Ferdinand Urban
 *
 *
 */

package cz.xmartcar.communication.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpErrorException  extends BaseException {

    private static final Logger log = LoggerFactory.getLogger(HttpErrorException.class);

    private static final long serialVersionUID = -1230984567657658101L;

    private int mResponsecode;
    private String mErrorResponse;

    public HttpErrorException(String msg) {
        super(msg);
    }

    public HttpErrorException(String msg, Throwable exception) {
        super(msg, exception);
    }

    public HttpErrorException(int responsecode, String errorResponse, String msg, Throwable exception) {
        super(msg, exception);

        mResponsecode = responsecode;
        mErrorResponse = errorResponse;
    }

    public int getResponsecode() {
        return mResponsecode;
    }

    public String getErrorResponse() {
        return mErrorResponse;
    }

}
