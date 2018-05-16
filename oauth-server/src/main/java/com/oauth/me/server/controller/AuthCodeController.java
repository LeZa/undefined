package com.oauth.me.server.controller;


import com.oauth.me.server.service.UserDetailService;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("code")
public class AuthCodeController {


    @Autowired
    private UserDetailService userService;


    @RequestMapping(value="/response")
    @ResponseBody
    public String  responseCode(HttpServletRequest request)
            throws OAuthProblemException, OAuthSystemException {
        OAuthAuthzRequest  oAuthAuthzRequest = new OAuthAuthzRequest( request );
        if( !StringUtils.isEmpty( oAuthAuthzRequest.getClientId()) ){

            String authorizationCode ="authorizationCode";
            String responseType = oAuthAuthzRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
            OAuthASResponse.OAuthAuthorizationResponseBuilder builder =
                    OAuthASResponse.authorizationResponse(request, HttpServletResponse.SC_FOUND);
            builder.setCode(authorizationCode);
            String redirectURI = oAuthAuthzRequest.getParam(OAuth.OAUTH_REDIRECT_URI);
            final OAuthResponse response = builder.location(redirectURI).buildQueryMessage();
            String responceUri =response.getLocationUri();
            HttpHeaders headers =new HttpHeaders();
        }
        return null;
    }
}
