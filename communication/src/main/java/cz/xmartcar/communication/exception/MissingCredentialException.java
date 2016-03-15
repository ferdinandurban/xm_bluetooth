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

public class MissingCredentialException extends BaseException {

    private static final Logger log = LoggerFactory.getLogger(MissingCredentialException.class);

    private static final long serialVersionUID = -1230984567657658103L;

    public MissingCredentialException(String message) {
        super(message);
    }

    public MissingCredentialException(String message, Throwable exception) {
        super(message, exception);
    }

}