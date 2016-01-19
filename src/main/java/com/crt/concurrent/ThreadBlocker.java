package com.crt.concurrent;

public class ThreadBlocker {

    private final Object lock = new Object();
    private volatile boolean blocked;

    public boolean isBlocked() {
        return blocked;
    }

    public void block() {
        synchronized (lock) {
            blocked = true;
            lock.notifyAll();
        }
    }

    public void unblock() {
        synchronized (lock) {
            blocked = false;
            lock.notifyAll();
        }
    }

    public void waitIfBlocked() throws InterruptedException {
        synchronized (lock) {
            while (blocked) {
                lock.wait();
            }
        }
    }
}
