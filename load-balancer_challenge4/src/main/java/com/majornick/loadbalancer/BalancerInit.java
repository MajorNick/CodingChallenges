package com.majornick.loadbalancer;

import com.majornick.loadbalancer.utils.CircularQueue;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class BalancerInit implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        CircularQueue<String> queue = new CircularQueue<>();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
