package com.productapi.config;

import jakarta.servlet.ServletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    // @Override
//    public void handle(HttpServletRequest request, HttpServletResponse response,
//                       AccessDeniedException accessDeniedException) throws IOException {
//
//        // Log the denied request details
//        logger.warn("Access denied for URL: {} | Method: {} | User: {}",
//                request.getRequestURI(), request.getMethod(), request.getRemoteUser());
//
//        // You can redirect the user to a custom access denied page or return a 403 response
//        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
//    }

    @Override
    public void handle(jakarta.servlet.http.HttpServletRequest request,
                       jakarta.servlet.http.HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // Log the denied request details
        logger.warn("Access denied for URL: {} | Method: {} | User: {}",
                request.getRequestURI(), request.getMethod(), request.getRemoteUser());

        // You can redirect the user to a custom access denied page or return a 403 response
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
    }
}

