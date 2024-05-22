package com.example.admin.security;

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

        httpResponse.setHeader("Content-Security-Policy",
                "default-src 'self';"
                        + "frame-ancestors 'none';"
                        + "form-action 'self';"
                        + "style-src 'self' https://fonts.googleapis.com;"
                        + "script-src 'self';"
                        + "font-src 'self' https://fonts.gstatic.com;"
                        + "img-src 'self' https://source.unsplash.com https://images.unsplash.com;");

        chain.doFilter(request, response);
    }
}
