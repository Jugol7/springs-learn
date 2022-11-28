package com.imooc.spring.escape.http_request_response;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 请求包装器过滤器
 *
 * @author zlp
 * @date 2022/11/28
 */
@Slf4j
@WebFilter(urlPatterns = "/*", filterName = "RequestWrapperFilter")
public class RequestWrapperFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        log.info("RequestWrapperFilter Replace InputStream!");
        // 过滤器处理每一个请求，将Request换成自定义的，可重复读取
        ServletRequest requestWrapper =
                new RequestWrapper((HttpServletRequest) request);
        chain.doFilter(requestWrapper, response);
    }

    @Override
    public void destroy() {

    }
}
