package com.crt.concurrent;

import java.io.IOException;
import java.io.InputStream;

public class Producer implements Runnable {

    private final InputStream stream;
    private final ExchangePoint<byte[]> exchangePoint;
    private final byte[] readBuffer;

    public Producer(InputStream stream, ExchangePoint<byte[]> exchangePoint, int bufSize) {
        this.stream = stream;
        this.exchangePoint = exchangePoint;
        readBuffer = new byte[bufSize];
    }

    @Override
    public void run() {
        try {
            int bytesRead = stream.read(readBuffer);
            if (bytesRead == -1) {
                Utils.log("Producer reached end-of-file");
                exchangePoint.put(Utils.EOF);
                Thread.currentThread().interrupt();
            } else if (bytesRead > 0) {
                byte[] bytes = new byte[bytesRead];
                System.arraycopy(readBuffer, 0, bytes, 0, bytesRead);
                Utils.log("Producer read " + bytes.length + " bytes");
                exchangePoint.put(bytes);
            }
        } catch (InterruptedException ignore) {
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
