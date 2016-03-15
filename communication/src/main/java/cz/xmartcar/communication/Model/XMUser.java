/*
 *
 * @author Ferdinand Urban
 * Copyright (C) 2016  Ferdinand Urban
 *
 *
 */

package cz.xmartcar.communication.model;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class XMUser {
    private String mName;
    private String mPassword;
    private String mToken;
    private String mTokenType;
    private Integer mExpiresIn;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }

    public String getTokenType() {
        return mTokenType;
    }

    public void setmTokenType(String tokenType) {
        mTokenType = tokenType;
    }

    public Integer getExpiresIn() {
        return mExpiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        mExpiresIn = expiresIn;
    }

    public Map<String,XMCar> getCars() throws IOException {
        return null;
    }

    public List<XMEventInfo> listEvents() throws IOException {
        return null;
    }
}
