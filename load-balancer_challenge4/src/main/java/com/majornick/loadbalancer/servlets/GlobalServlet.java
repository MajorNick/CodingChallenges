package com.majornick.loadbalancer.servlets;


import com.majornick.loadbalancer.models.Server;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@WebServlet(name = "global", urlPatterns = "/global")
public class GlobalServlet extends HttpServlet {
    private static final ArrayBlockingQueue<Server> queue;
    private static final ArrayBlockingQueue<Server> faultyServers;

    static {
        faultyServers = new ArrayBlockingQueue<>(10);
        queue = new ArrayBlockingQueue<>(10);
        queue.add(new Server("http://localhost:8081"));
        queue.add(new Server("http://localhost:8082"));
        serverHealthCheck();
    }


    private static void addFaultServer(Server server) {
        faultyServers.add(server);
    }

    public static void serverHealthCheck() {
        ScheduledExecutorService service = new ScheduledThreadPoolExecutor(1);
        service.scheduleAtFixedRate(() -> {
            int k = faultyServers.size();
            while ((k--) > 0) {
                Server server = faultyServers.poll();
                try {
                    if (server != null) {
                        server.testServer();

                    }
                } catch (IOException e) {
                    System.err.printf("%s is down\n", server.getUrl());
                    faultyServers.add(server);
                }
            }
        }, 10, 10, TimeUnit.SECONDS);


    }


    public static ArrayBlockingQueue<Server> getQueue() {
        return queue;
    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

        if (queue.peek() == null) {
            return;
        }
        Server server = queue.poll();
        try {
            server.testServer();
            httpServletResponse.sendRedirect(server.getUrl().toString());
            queue.add(server);
        } catch (IOException e) {
            faultyServers.add(server);
            System.err.printf("%s is faulty",server.getUrl());
        }


    }
}
