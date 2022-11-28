package com.imooc.spring.escape.http_request_response;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * 请求包装器过滤器
 * 1. Request 中的 getInputStream 和 getReader 都只能用一次（读取一次）
 * 2. Request 中的 getInputStream 和 getReader 和 getParameter 是互斥的，调用其中一个，再使用另外的方法是获取不到数据的，直接返回了
 * Response 也是一样的
 * 解析第一点：
 * getInputStream()：源码分析是由于在ServletInputStream中，读取数据的数据坐标指针是没有重置的，读完之后，坐标指针一直都是最后，所以读取不到数据
 * getReader()：也是一样的原理
 * 解析第二点：
 * 存在两个boolean变量，usingReader、usingInputStream，在调用了对应方法之后，然后置为 true
 * 同样可以实现一个 ResponseWrapper
 *
 * @author zlp
 * @date 2022/11/28
 */
public class RequestWrapper extends HttpServletRequestWrapper {

    /**
     * 存储输入流数据
     */
    private final byte[] body;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public RequestWrapper(HttpServletRequest request) {
        super(request);
        // 保存数据流中的数据
        body = RequestParseUtil.getBodyString(request).getBytes(
                Charset.defaultCharset());
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    /**
     * 可多次调用使用请求数据流中的信息
     *
     * @return
     * @throws IOException
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {

        ByteArrayInputStream inputStream = new ByteArrayInputStream(body);

        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }

            @Override
            public int read() throws IOException {
                return inputStream.read();
            }
        };
    }
}
