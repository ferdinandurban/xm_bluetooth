/*
 *
 * @author Ferdinand Urban
 * Copyright (C) 2016  Ferdinand Urban
 *
 *
 */

package cz.xmartcar.communication.base;


public class UserAgentHeader {

    private String productId;
    private String productVersion;

    public UserAgentHeader(String productId, String productVersion) {
        super();
        this.productId = productId != null && productId.trim().length() > 0 ? productId
                : "";
        this.productVersion = productVersion != null
                && productVersion.trim().length() > 0 ? productVersion : "";
    }


}