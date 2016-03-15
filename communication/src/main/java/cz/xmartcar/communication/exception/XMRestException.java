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

import cz.xmartcar.communication.model.XMJSONFormatter;

public class XMRestException extends Exception {
    private static final Logger log = LoggerFactory.getLogger(XMRestException.class);
    private static final long serialVersionUID = -1230984567657658104L;

    /**
     * If source is {@link HttpErrorException},
     * exception's response code value is copied
     */
    private int responsecode;

    /**
     * If source is {@link HttpErrorException} and response code is 400,
     * error response content is converted to {@link Error} object
     */
    private Error details;

    public XMRestException(String message) {
        super(message);
    }

    public XMRestException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public XMRestException(Throwable throwable) {
        super(throwable);
    }

    public int getResponsecode() {
        return responsecode;
    }

    public void setResponsecode(int responsecode) {
        this.responsecode = responsecode;
    }

    public Error getDetails() {
        return details;
    }

    public void setDetails(Error details) {
        this.details = details;
    }

    protected static XMRestException createFromHttpErrorException(HttpErrorException httpErrorException){
        XMRestException xmRestException = new XMRestException(httpErrorException.getMessage(), httpErrorException);
        xmRestException.setResponsecode(httpErrorException.getResponsecode());

        if( httpErrorException.getResponsecode() >= 400 &&  httpErrorException.getErrorResponse()!=null){

            try{
                Error details = XMJSONFormatter.fromJSON(httpErrorException.getErrorResponse(), Error.class);
                xmRestException.setDetails(details);
            } catch(Exception e){
                log.error("Exception thrown while parsing error response: " + httpErrorException.getErrorResponse() , e);
            }
        }

        return xmRestException;
    }

    public String toString() {
        return "response-code: " + this.responsecode + "\tdetails: " + this.details;
    }

}