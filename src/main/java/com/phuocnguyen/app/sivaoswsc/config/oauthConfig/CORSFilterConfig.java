package com.phuocnguyen.app.sivaoswsc.config.oauthConfig;

import com.sivaos.Configurer.CustomFilterRequest.TypeSafeRequest;
import org.springframework.stereotype.Component;

import javax.servlet.*;

@Component
public class CORSFilterConfig implements Filter {

    public CORSFilterConfig() {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) {
        TypeSafeRequest.buildServletDoFilterChain(servletRequest, servletResponse, chain);
    }

    public void init(FilterConfig fConfig) {
        TypeSafeRequest.init(fConfig);
    }
}
