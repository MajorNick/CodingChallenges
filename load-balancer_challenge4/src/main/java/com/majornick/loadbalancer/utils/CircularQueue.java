package com.majornick.loadbalancer.utils;

import java.util.ArrayDeque;


public class CircularQueue<E> extends ArrayDeque<E> {
    @Override
    public E poll() {

        E e = super.poll();
        if (e == null) {
            return null;
        }
        super.add(e);
        return e;
    }
}
