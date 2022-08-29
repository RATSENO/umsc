package com.org.umsc.auth.jwt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtHeaderUtil {
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    public static String getAccessToken(HttpServletRequest request){
        String headerValue = request.getHeader(HEADER_AUTHORIZATION);
        String accessToken = null;
        if(headerValue==null){
            return null;
        }
        if(headerValue.startsWith(TOKEN_PREFIX)){
            accessToken = headerValue.substring(TOKEN_PREFIX.length());
        }
        return accessToken;
    }
}
