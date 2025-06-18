package com.example.producer.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import java.io.IOException;

@Component
public class RequestLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String uri = httpRequest.getRequestURI();
        String contentType = httpRequest.getContentType();
        String method = httpRequest.getMethod();

        // Skip logging for non-JSON or non-API routes
        boolean isJson = contentType != null && contentType.contains("application/json");
        boolean isApi = uri.startsWith("/api/");
        boolean isModifyingMethod = method.equalsIgnoreCase("POST") || method.equalsIgnoreCase("PUT");

        if (!(isJson && isApi && isModifyingMethod)) {
            // Just pass the request through with no logging
            chain.doFilter(request, response);
            return;
        }

        // Proceed with wrapping and logging
        CachedBodyHttpServletRequest wrappedRequest = new CachedBodyHttpServletRequest(httpRequest);
        CachedBodyHttpServletResponse wrappedResponse = new CachedBodyHttpServletResponse((HttpServletResponse) response);

        logger.info("Incoming Request: [{}] {}", method, uri);
        logger.info("Request Body: {}", wrappedRequest.getBodyAsString());

        chain.doFilter(wrappedRequest, wrappedResponse);

        logger.info("Response Body: {}", wrappedResponse.getCapturedBodyAsString());

        response.getOutputStream().write(wrappedResponse.getCapturedBody());
    }


}
