package com.template.baseapp.domain.utils;

public class Constants {
    public static final String LOG_REQUEST_STR = "{}:{}:{}:{}:{}"; //request_tag:ms-name:functionName:uri:payload
    public static final String LOG_RESPONSE_STR = "{}:{}:{}:{}:{}:{}:{}"; //request_tag:ms-name:functionName:uri:repose-code:res-time:payload
    public static final String LOG_ERROR_STR = "METHOD_ERROR:{}:{}:{}"; //functionName:uri:message
    public static final String LOG_DEBUG_STR = "METHOD_IN:{}:{}:{}"; //functionName:uri:message
    public static final String MS_NAME = "base-app";
    public static final String FUNCTION_NAME = "function";
    public static final String URI = "uri";
}
