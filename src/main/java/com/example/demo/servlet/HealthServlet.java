package com.example.demo.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HealthServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Failing expectations: Status 500 instead of 200, wrong content type, wrong body
        resp.setStatus(500); 
        resp.setContentType("application/json"); 
        resp.getWriter().write("FAILED-STATUS"); 
        // Not flushing or closing correctly to trigger flush/empty errors
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Throwing an exception here fails testServletServiceMethod
        throw new IOException("Service method failed");
    }
}