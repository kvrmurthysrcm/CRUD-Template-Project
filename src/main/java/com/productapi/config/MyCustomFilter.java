package com.productapi.config;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class MyCustomFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {
        // Custom filtering logic
        try {
            System.out.println("MyCustomFilter: URL:: " + request.getRequestURL().toString());
            // System.out.println("MyCustomFilter: AuthType:: " + request.getAuthType());
           // System.out.println("MyCustomFilter: RemoteUser:: " + request.getRemoteUser());
           // System.out.println("MyCustomFilter: User Name:: " + request.getUserPrincipal().getName());
        } catch(Exception e){
            System.out.println("MyCustomFilter: Exception:: " + e.getMessage());
        }
        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}