/*
 *
 * @author Ferdinand Urban
 * Copyright (C) year  Ferdinand Urban
 *
 *
 */

package cz.xmartcar.communication.base;

public final class XMConstants {

    private XMConstants() {}

    // General Constants
    // UTF-8 Encoding format
    public static final String ENCODING_FORMAT = "UTF-8";

    // Empty String
    public static final String EMPTY_STRING = "";


    // Default SDK configuration file name
    public static final String DEFAULT_CONFIGURATION_FILE = "comm_config.properties";

    // HTTP Content-Type Header
    public static final String HTTP_CONTENT_TYPE_HEADER = "Content-Type";

    // HTTP Accept Header
    public static final String HTTP_ACCEPT_HEADER = "Accept";

    // User Agent Header
    public static final String USER_AGENT_HEADER = "User-Agent";

    // Authorization Header
    public static final String AUTHORIZATION_HEADER = "Authorization";

    // HTTP Configurations Defaults
    // HTTP Methods
    public static final String HTTP_CONFIG_POST_HTTP_METHOD = "POST";
    public static final String HTTP_CONFIG_GET_HTTP_METHOD = "GET";
    public static final String HTTP_CONFIG_PUT_HTTP_METHOD = "PUT";
    public static final String HTTP_CONFIG_DELETE_HTTP_METHOD = "DELETE";

    // HTTP Method Default
    public static final String HTTP_CONFIG_DEFAULT_HTTP_METHOD = HTTP_CONFIG_POST_HTTP_METHOD;

    // HTTP Content Type Default
    public static final String HTTP_CONFIG_DEFAULT_CONTENT_TYPE = "application/x-www-form-urlencoded";

    // HTTP Content Type JSON
    public static final String HTTP_CONTENT_TYPE_JSON = "application/json";

    // HTTP Content Type Patch JSON
    public static final String HTTP_CONTENT_TYPE_PATCH_JSON = "application/json-patch+json";

    // HTTP Content Type XML
    public static final String HTTP_CONTENT_TYPE_XML = "text/xml";

    // IPN endpoint property name
    public static final String IPN_ENDPOINT = "service.IPNEndpoint";

    // Platform SSO Endpoint
    public static final String PLATFORM_SSO_ENDPOINT = "https://vcartestid1/xMartOnSSO/connect";

    // REST Sandbox Endpoint
    public static final String REST_SANDBOX_ENDPOINT = "https://api.sandbox.xmarton.com/";

    // REST Live Endpoint
    public static final String REST_LIVE_ENDPOINT = "https://api.xmarton.com/";
}