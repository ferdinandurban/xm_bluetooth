/*
 *
 * @author Ferdinand Urban
 * Copyright (C) 2016  Ferdinand Urban
 *
 *
 */

package cz.xmartcar.communication.model;

public class XMTokenInfo {

    private String mScope;
    private String mAccessToken;
    private String mRefreshToken;
    private String mTokenType;
    private Integer mExpiresIn;
    private String mAppId;

    public XMTokenInfo() {
    }

    public XMTokenInfo(String accessToken, String tokenType, Integer expiresIn) {
        mAccessToken = accessToken;
        mTokenType = tokenType;
        mExpiresIn = expiresIn;
    }

    public void setScope(String scope) {
        mScope = scope;
    }

    public String getScope() {
        return mScope;
    }

    public void setAccessToken(String accessToken) {
        mAccessToken = accessToken;
    }

    public String getAccessToken() {
        return mAccessToken;
    }

    public String getAccessTokenWithType() {
        return mTokenType + " " + mAccessToken;
    }

    public void setRefreshToken(String refreshToken) {
        mRefreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return mRefreshToken;
    }

    public void setTokenType(String tokenType) {
        mTokenType = tokenType;
    }

    public String getTokenType() {
        return mTokenType;
    }

    public void setExpiresIn(Integer expiresIn) {
        mExpiresIn = expiresIn;
    }

    public Integer getExpiresIn() {
        return mExpiresIn;
    }

    public void setAppId(String appId) {
        mAppId = appId;
    }

    public String getAppId() {
        return mAppId;
    }
}