package com.crt.concurrent;

public class Consumer implements Runnable {

    private final ExchangePoint<byte[]> exchangePoint;

    public Consumer(ExchangePoint<byte[]> exchangePoint) {
        this.exchangePoint = exchangePoint;
    }

    @Override
    public void run() {
        try {
            byte[] take = exchangePoint.take();
            if (take == Utils.EOF) {
                Utils.log("Consumer received end-of-file signal");
                Thread.currentThread().interrupt();
            } else {
                Utils.log("Consumer received " + take.length + " bytes");
            }
        } catch (InterruptedException ignore) {
            Thread.currentThread().interrupt();
        }
    }
}
