package com.example.demo.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HealthServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setStatus(200); [cite_start]// [cite: 282, 283]
        resp.setContentType("text/plain"); [cite_start]// [cite: 282, 283]
        resp.getWriter().write("BUNDLE-OK"); [cite_start]// [cite: 282, 283]
    }
}