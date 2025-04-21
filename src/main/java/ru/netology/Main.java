package ru.netology;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static final int QUEUE_SIZE = 100;
    public static final int TEXT_QUANTITY = 10_000;
    public static final int TEXT_SIZE = 100_000;

    private static final List<BlockingQueue<String>> queues = Arrays.asList(
            new ArrayBlockingQueue<>(QUEUE_SIZE),
            new ArrayBlockingQueue<>(QUEUE_SIZE),
            new ArrayBlockingQueue<>(QUEUE_SIZE)
    );


    public static void main(String[] args) throws InterruptedException {

        Thread producer = new Thread(() -> {
            for (int i = 0; i < TEXT_QUANTITY; i++) {
                String str = generateText("abc", TEXT_SIZE);
                try {
                    for(BlockingQueue<String> queue:queues) {
                        queue.put(str);
                    }
                } catch (InterruptedException e) {
                    return;
                }
            }
        });
        producer.start();

        List<Thread> consumers = new ArrayList<>();
        for(int i = 0; i < queues.size(); i++){
            Thread consumer = new Thread(new MyRunnable(queues.get(i), (char)('a' + i)));
            consumers.add(consumer);
            consumer.start();
        }
        for(Thread consumer : consumers){
            consumer.join();
        }
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}