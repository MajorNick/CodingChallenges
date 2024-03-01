package com.majornick.loadbalancer.utils;

import com.majornick.loadbalancer.models.Server;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;


public class CircularQueue<E> extends ArrayBlockingQueue<E> {
    public CircularQueue(int capacity) {
        super(capacity);
    }



    public E pollAndReturn() {
        E e = super.poll();
        if (e == null) {
            return null;
        }
        super.add(e);
        return e;
    }


}
