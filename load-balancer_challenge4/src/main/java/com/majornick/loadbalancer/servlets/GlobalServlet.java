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
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@WebServlet(name = "global", urlPatterns = "/global")
public class GlobalServlet extends HttpServlet  {
    private static final CircularQueue<Server> queue;
    private static final ArrayBlockingQueue<Server> faultyServers;
    static{
        faultyServers = new ArrayBlockingQueue<>(10);
        queue =  new CircularQueue<>(10);
        queue.add(new Server("http://localhost:8081"));
        queue.add(new Server("http://localhost:8082"));
        serverHealthCheck();
    }


    private static void addFaultServer(Server server){
            faultyServers.add(server);
    }

    public static void serverHealthCheck(){
        ScheduledExecutorService service = new ScheduledThreadPoolExecutor(1);
        service.scheduleAtFixedRate(()->{
            int k = faultyServers.size();
            while((k--)>0){
                Server server = faultyServers.poll();
                try{
                    if (server != null) {
                        server.testServer();
                    }
                }catch (IOException e){
                    System.out.printf("%s is down",server.getUrl());
                    faultyServers.add(server);
                }
            }
        },10,10,TimeUnit.SECONDS);


    }


    public static CircularQueue<Server> getQueue() {
        return queue;
    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

        if (queue.peek() == null) {
            return;
        }
        try{
            queue.peek().testServer();
            String str = queue.pollAndReturn().getUrl().toString();

            httpServletResponse.sendRedirect(str);
        }catch(IOException e) {
            System.out.println(queue.peek().getUrl().toString());
        }


    }
}
