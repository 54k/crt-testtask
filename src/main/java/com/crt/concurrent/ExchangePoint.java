package com.crt.concurrent;

import java.util.concurrent.SynchronousQueue;

public class ExchangePoint<V> {

    private final SynchronousQueue<V> sync = new SynchronousQueue<V>();

    public void put(V value) throws InterruptedException {
        sync.put(value);
    }

    public V take() throws InterruptedException {
        return sync.take();
    }
}
