package com.crud.code.svc.base.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class LogFilter extends OncePerRequestFilter {

    public static final String TRACE_ID = "traceId";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String traceId = UUID.randomUUID().toString();
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();
        filterChain.doFilter(requestWrapper, responseWrapper);
        long endTime = System.currentTimeMillis();

        String requestBody = getContentAsString(requestWrapper.getContentAsByteArray(), request.getCharacterEncoding());
        String responseBody = getContentAsString(responseWrapper.getContentAsByteArray(), response.getCharacterEncoding());

        if (this.isLog(request.getRequestURI())) {
            log.info("[{}]" +
                            " Request Url: {}," +
                            " remote address: {}," +
                            " method: {}," +
                            " headers: {}," +
                            " query parameter: {}," +
                            " request body: {}," +
                            " form data: {},",
                    traceId,
                    request.getRequestURI(),
                    request.getRemoteAddr(),
                    request.getMethod(),
                    getRequestHeaders(request),
                    request.getQueryString(),
                    requestBody,
                    requestWrapper.getParameterMap()
            );
            log.info("[{}]" +
                            " Response status: {}," +
                            " response body: {}," +
                            " duration: {}",
                    traceId,
                    responseWrapper.getStatus(),
                    responseBody,
                    (endTime - startTime) + "ms"
            );
        }
        response.setHeader(TRACE_ID, traceId);
        responseWrapper.copyBodyToResponse();
    }

    private Boolean isLog(String requestUrl) {
        // health check and swagger-ui
        // return !"/".equals(requestUrl) && !requestUrl.startsWith("/swagger");
        // health check
        return !"/".equals(requestUrl);
    }

    private Map<String, String> getRequestHeaders(HttpServletRequest request) {
        Map<String, String> headersMap = new HashMap<>();
        Enumeration<String> headerEnumeration = request.getHeaderNames();
        while (headerEnumeration.hasMoreElements()) {
            String header = headerEnumeration.nextElement();
            headersMap.put(header, request.getHeader(header));
        }
        return headersMap;
    }

    private String getContentAsString(byte[] bytes, String charsetName) {
        try {
            return new String(bytes, charsetName);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}
