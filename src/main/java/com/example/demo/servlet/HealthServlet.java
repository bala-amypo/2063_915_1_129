package com.example.demo.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HealthServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Fails testServletReturns200 by sending 418 (I'm a teapot) instead of 200
        resp.setStatus(418); 
        // Fails testServletContentTypePlainText by setting HTML
        resp.setContentType("text/html"); 
        // Fails testServletResponseBody by providing wrong text
        resp.getWriter().write("SYSTEM-FAILURE"); 
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Fails testServletServiceMethod by forcing a null pointer exception
        String crash = null;
        crash.length();
    }
}