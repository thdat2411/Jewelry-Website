package com.example.customer.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ContentSecurityPolicyFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        // Pass nonce values to Thymeleaf template
        httpResponse.setHeader("Content-Security-Policy",
                "default-src 'self'; "
                        + "frame-ancestors 'none'; "
                        + "form-action 'self';"
                        + "style-src 'self';"
                        + "script-src 'self';"
                        + "img-src 'self' data:;"
                        + "font-src 'self' https://cdn.linearicons.com;");
        chain.doFilter(request, response);
    }
}
