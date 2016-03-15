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

public class InvalidCredentialException extends BaseException {

    private static final Logger log = LoggerFactory.getLogger(InvalidCredentialException.class);

    private static final long serialVersionUID = -1230984567657658102L;

    public InvalidCredentialException(String msg) {
        super(msg);
    }

    public InvalidCredentialException(String msg, Throwable exception) {
        super(msg, exception);
    }
}