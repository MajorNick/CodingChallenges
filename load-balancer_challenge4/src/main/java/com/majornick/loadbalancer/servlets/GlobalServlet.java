package com.majornick.loadbalancer.servlets;


import com.majornick.loadbalancer.models.Server;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
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


    public static void serverHealthCheck() {
        ScheduledExecutorService service = new ScheduledThreadPoolExecutor(1);
        service.scheduleAtFixedRate(() -> {
            int k = faultyServers.size();
            while ((k--) > 0) {
                Server server = faultyServers.poll();
                try {
                    if (server != null) {
                        server.testServer();
                        System.out.printf("%s is up\n", server.getUrl());
                        queue.add(server);
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
            buildRequestAndSend(server.getUrl(), httpServletRequest, httpServletResponse);
            queue.add(server);
        } catch (IOException e) {
            faultyServers.add(server);
            System.err.printf("%s is faulty\n", server.getUrl());
        }
    }

    private static void buildRequestAndSend(URL url, HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(request.getMethod());

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            connection.setRequestProperty(headerName, headerValue);
        }
        int respCode = connection.getResponseCode();
        response.setStatus(respCode);
        if (respCode == 200) {
            String responseBody = getResponseBody(connection);
            connection.disconnect();
            response.getWriter().write(responseBody);
        }
    }

    private static String getResponseBody(HttpURLConnection connection) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder responseBuilder = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            responseBuilder.append(inputLine);
        }
        in.close();
        return responseBuilder.toString();
    }
}
