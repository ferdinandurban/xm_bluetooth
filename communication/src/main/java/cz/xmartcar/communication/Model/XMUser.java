/*
 *
 * @author Ferdinand Urban
 * Copyright (C) 2016  Ferdinand Urban
 *
 *
 */

package cz.xmartcar.communication.Model;

public class XMUser {
    private String mName;
    private String mPassword;
    private String mToken;
    private String mTokenType;
    private Integer mExpiresIn;

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmToken() {
        return mToken;
    }

    public void setmToken(String mToken) {
        this.mToken = mToken;
    }

    public String getmTokenType() {
        return mTokenType;
    }

    public void setmTokenType(String mTokenType) {
        this.mTokenType = mTokenType;
    }

    public Integer getmExpiresIn() {
        return mExpiresIn;
    }

    public void setmExpiresIn(Integer mExpiresIn) {
        this.mExpiresIn = mExpiresIn;
    }

}
