package com.majornick.loadbalancer.servlets;


import com.majornick.loadbalancer.utils.CircularQueue;
import models.Server;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "global", urlPatterns = "/*")
public class GlobalServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        ServletContext context = httpServletRequest.getServletContext();
        CircularQueue<Server> queue = (CircularQueue<Server>) context.getAttribute("queue");
        if (queue.peek() == null) {
            return;
        }
        if (!queue.peek().testServer()) {
            httpServletResponse.sendRedirect(queue.pollAndReturn().toString());
        }
    }
}
