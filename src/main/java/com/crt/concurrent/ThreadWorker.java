package com.crt.concurrent;

import java.util.Objects;

public class ThreadWorker implements Runnable {

    private final ThreadBlocker blocker = new ThreadBlocker();
    private final Runnable target;

    public ThreadWorker(Runnable target) {
        Objects.requireNonNull(target);
        this.target = target;
    }

    public void run() {
        try {
            run0();
        } catch (InterruptedException ignore) {
            // Was interrupted
        }
    }

    private void run0() throws InterruptedException {
        //noinspection InfiniteLoopStatement
        while (true) {
            blocker.waitIfBlocked();
            target.run();
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();
            }
        }
    }

    public boolean isBlocked() {
        return blocker.isBlocked();
    }

    public void block() {
        blocker.block();
    }

    public void unblock() {
        blocker.unblock();
    }
}
