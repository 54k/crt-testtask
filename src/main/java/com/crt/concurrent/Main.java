package com.crt.concurrent;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws Exception {
        String filePath = args[0];
        int bufSize = 1024;
        if (args.length > 1) {
            bufSize = Integer.valueOf(args[1]);
        }

        try (FileInputStream fileStream = new FileInputStream(filePath);
             BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {
            ExchangePoint<byte[]> exchangePoint = new ExchangePoint<>();
            ThreadWorker producer = new ThreadWorker(new Producer(fileStream, exchangePoint, bufSize));
            ThreadWorker consumer = new ThreadWorker(new Consumer(exchangePoint));

            Thread thread1 = new Thread(producer, "producer-thread");
            thread1.start();
            Thread thread2 = new Thread(consumer, "consumer-thread");
            thread2.start();

            while (consoleReader.readLine() != null) {
                if (thread1.isAlive() && thread2.isAlive()) {
                    if (producer.isBlocked()) {
                        producer.unblock();
                        Utils.log("Producer unblocked");
                    } else {
                        producer.block();
                        Utils.log("Producer blocked");
                    }
                } else {
                    Utils.log("Producer and consumer finished their work");
                    break;
                }
            }
        }
    }
}
