package com.alibaba.boot.dubbo.demo.consumer.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableResourceServer
public class ResourceServerConfiguration
        extends ResourceServerConfigurerAdapter {

    @Autowired
    private RedisConnectionFactory connectionFactory;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        OAuth2AuthenticationProcessingFilter f = new OAuth2AuthenticationProcessingFilter();
        OAuth2AuthenticationEntryPoint oAuth2AuthenticationEntryPoint = new OAuth2AuthenticationEntryPoint();
        OAuth2AuthenticationManager o = new OAuth2AuthenticationManager();
        DefaultTokenServices dt = new DefaultTokenServices();

        oAuth2AuthenticationEntryPoint.setExceptionTranslator(webResponseExceptionTranslator());
        f.setAuthenticationEntryPoint(oAuth2AuthenticationEntryPoint);

        dt.setTokenStore(tokenStore());
        o.setTokenServices(dt);
        f.setAuthenticationManager(o);
        http.antMatcher("/**").exceptionHandling().authenticationEntryPoint(getExceptionEntryPoint());
      /*  http.addFilterAfter(f,AbstractPreAuthenticatedProcessingFilter.class);
        http.addFilterBefore(f,AbstractPreAuthenticatedProcessingFilter.class);
        http.exceptionHandling().accessDeniedHandler(getAccessDeniedHandler());
        http.addFilter(f);*/
//        http.antMatcher("/**").authorizeRequests().anyRequest().authenticated().and().addFilterAfter(f,AbstractPreAuthenticatedProcessingFilter.class).addFilterBefore(f,
//                AbstractPreAuthenticatedProcessingFilter.class);
    }


    @Bean
    public RedisTokenStore tokenStore() {
        return new RedisTokenStore(connectionFactory);
    }


    @Bean
    public AuthenticationEntryPoint getExceptionEntryPoint() {
        return (httpServletRequest, httpServletResponse, e) -> {
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json; charset=utf-8");
            PrintWriter out = null;
            out = httpServletResponse.getWriter();
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("msg", "参数错误或未通过认证");
            resultMap.put("code", "-1");
            resultMap.put("data", new ArrayList());
            Gson gson2 = new GsonBuilder().enableComplexMapKeySerialization().create();
            try {
                out = httpServletResponse.getWriter();
                out.append(gson2.toJson(resultMap));
            } finally {
                if (out != null) {
                    out.close();
                }
            }
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,gson2.toJson(resultMap));
        };
    }


    @Bean
    public WebResponseExceptionTranslator webResponseExceptionTranslator() {
        return new DefaultWebResponseExceptionTranslator() {
            @Override
            public ResponseEntity translate(Exception e) throws Exception {
                ResponseEntity responseEntity = super.translate(e);
                OAuth2Exception body = (OAuth2Exception) responseEntity.getBody();
                HttpHeaders headers = new HttpHeaders();
                headers.setAll(responseEntity.getHeaders().toSingleValueMap());
                // do something with header or response

                if (401 == responseEntity.getStatusCode().value()) {
                    Map<String, Object> resultMap = new HashMap<String, Object>();
                    resultMap.put("msg", "未获取授权");

                    return new ResponseEntity(resultMap, headers, responseEntity.getStatusCode());
                } else {
                    return new ResponseEntity(body, headers, responseEntity.getStatusCode());
                }

            }
        };
    }

    @Bean
    public AccessDeniedHandler getAccessDeniedHandler(){
        return (httpServletRequest, httpServletResponse, e) -> {
            if(httpServletResponse.getStatus()  == 401 ){
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.setContentType("text/plain");
                httpServletResponse.getWriter().write("权限不足");
                httpServletResponse.getWriter().close();
            }
          /*  boolean isAjax = isAjaxRequest(httpServletRequest);
            if(isAjax){
                Message msg = MessageManager.exception(accessDeniedException);
                ControllerTools.print(response, msg);
            }else if (!response.isCommitted()) {
                if (errorPage != null) {
                    // Put exception into request scope (perhaps of use to a view)
                    request.setAttribute(WebAttributes.ACCESS_DENIED_403, accessDeniedException);

                    // Set the 403 status code.
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);

                    // forward to error page.
                    RequestDispatcher dispatcher = request.getRequestDispatcher(errorPage);
                    dispatcher.forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
                }
            }*/
        };
    }

    private boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        if (header != null && "XMLHttpRequest".equals(header))
            return true;
        else
            return false;
    }
}
