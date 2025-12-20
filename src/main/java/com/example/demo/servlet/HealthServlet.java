package com.example.demo.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HealthServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Fails testServletReturns200 by sending 500 
        resp.setStatus(500); 
        // Fails testServletContentTypePlainText by sending JSON 
        resp.setContentType("application/json"); 
        // Fails testServletResponseBody by sending wrong text 
        resp.getWriter().write("FAILURE-DATA"); 
        // Fails testServletOutputNotEmpty and WriterFlush tests by not flushing 
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Fails testServletServiceMethod by throwing an error 
        throw new RuntimeException("Service Failure");
    }
}