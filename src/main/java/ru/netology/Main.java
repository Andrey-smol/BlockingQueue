package ru.netology;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    private static final BlockingQueue<String> queue1 = new ArrayBlockingQueue<>(100);
    private static final BlockingQueue<String> queue2 = new ArrayBlockingQueue<>(100);
    private static final BlockingQueue<String> queue3 = new ArrayBlockingQueue<>(100);


    public static void main(String[] args) {


        new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                String str = generateText("abc", 100000);
                try {
                    queue1.put(str);
                    queue2.put(str);
                    queue3.put(str);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }).start();

        Thread threadA = new Thread(new MyRunnable(queue1, 'a'));

        Thread threadB = new Thread(new MyRunnable(queue2, 'b'));

        Thread threadC = new Thread(new MyRunnable(queue3, 'c'));

        threadA.start();
        threadB.start();
        threadC.start();

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