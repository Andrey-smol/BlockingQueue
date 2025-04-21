package ru.netology;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class MyRunnable implements Runnable {
    private final BlockingQueue<String> queue;
    private final char letter;

    public MyRunnable(BlockingQueue<String> queue, char letter) {
        this.queue = queue;
        this.letter = letter;
    }

    @Override
    public void run() {
        int max = 0;
        while (true) {
            try {
                String str = queue.poll(5, TimeUnit.SECONDS);
                int count = 0;
                if (str == null) {
                    break;
                }
                for (char c : str.toCharArray()) {
                    if (c == letter) {
                        count++;
                    }
                }
                if (count > max) {
                    max = count;
                }

            } catch (InterruptedException e) {
                break;
            }
        }
        System.out.printf("Максимальное количество символов '%c' = %d\n", letter, max);
    }
}
