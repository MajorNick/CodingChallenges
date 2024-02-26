package com.majornick.loadbalancer.utils;

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
