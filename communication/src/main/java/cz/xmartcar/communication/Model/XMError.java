/*
 *
 * @author Ferdinand Urban
 * Copyright (C) 2016  Ferdinand Urban
 *
 *
 */

package cz.xmartcar.communication.model;


public class XMError {

    private String mError;
    private String mErrorDescription;
    private String mErrorUri;

    public XMError() {
    }

    public XMError(String error) {
        mError = error;
    }

    public void setError(String error) {
        mError = error;
    }

    public String getError() {
        return mError;
    }

    public void setErrorDescription(String errorDescription) {
        mErrorDescription = errorDescription;
    }
    public String getErrorDescription() {
        return mErrorDescription;
    }

    public void setErrorUri(String errorUri) {
        mErrorUri = errorUri;
    }

    public String getErrorUri() {
        return mErrorUri;
    }

}
