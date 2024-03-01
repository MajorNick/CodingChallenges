package com.majornick.loadbalancer.servlets;


import com.majornick.loadbalancer.utils.CircularQueue;
import com.majornick.loadbalancer.models.Server;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@WebServlet(name = "global", urlPatterns = "/global")
public class GlobalServlet extends HttpServlet  {
    private static final CircularQueue<Server> queue;
    static{
        queue =  new CircularQueue<>(10);
        queue.add(new Server("http://localhost:8081"));
        queue.add(new Server("http://localhost:8082"));
    }

    public static void testServers(){
        ScheduledExecutorService service = new ScheduledThreadPoolExecutor(1);

    }

    public static CircularQueue<Server> getQueue() {
        return queue;
    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

        if (queue.peek() == null) {
            return;
        }
        if (queue.peek().testServer()) {
            String str = queue.pollAndReturn().getUrl().toString();
            System.out.println(str);
            httpServletResponse.sendRedirect(str);
        }
    }
}
