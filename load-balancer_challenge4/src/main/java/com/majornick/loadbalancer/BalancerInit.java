package com.majornick.loadbalancer;

import com.majornick.loadbalancer.utils.CircularQueue;
import com.majornick.loadbalancer.models.Server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/*
@WebListener
public class BalancerInit implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        CircularQueue<Server> queue = new CircularQueue<>(10);
        queue.add(new Server("http://localhost:8081"));
        //queue.add(new Server("http://localhost:8082"));
        servletContextEvent.getServletContext().setAttribute("queue", queue);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
*/
