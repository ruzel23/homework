package ru.sberbank.school.task10;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConcurrentScalableThreadPool implements ThreadPool {

    private final List<Thread> threads;
    private final BlockingQueue<Runnable> tasks;
    private int minSize;
    private int maxSize;

    ConcurrentScalableThreadPool(int minSize, int maxSize) {
        this.minSize = minSize;
        this.maxSize = maxSize;
        threads = new ArrayList<>(minSize);
        tasks = new ArrayBlockingQueue<>(maxSize);
    }

    @Override
    public void start() {
        for (int i = 0; i < minSize; i++) {
            ThreadWorker thread = new ThreadWorker("ThreadPoolWorker-" + i);
            threads.add(thread);
            thread.start();
            System.out.println("стартанул");
        }
    }

    @Override
    public void stopNow() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
        tasks.clear();
        threads.clear();
        System.out.println(threads.size());
    }

    @Override
    public void execute(Runnable runnable) {
        synchronized (tasks) {
        if (!tasks.isEmpty() && threads.size() >= minSize && threads.size() < maxSize) {
            ThreadWorker additionalThread = new ThreadWorker("ThreadPoolWorker-" + threads.size());
            threads.add(additionalThread);
            additionalThread.start();
            System.out.println("стартанул дополнительный");
        }
        try {
            System.out.println("собираюсь положить задачу");
            tasks.put(runnable);
            System.out.println("положил задачу");
        } catch (InterruptedException e) {
            //e.printStackTrace();
        }
        }
    }

    private class ThreadWorker extends Thread {

        ThreadWorker(String name) {
            super(name);
        }

        @Override
        public void run() {
            Runnable taskRun = null;
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(Thread.currentThread().getName());
                synchronized (tasks) {
                    System.out.println("начинаю работу");
                if (tasks.isEmpty() ) {
                    try {
                        if (threads.size() > minSize) {
                            Thread.currentThread().interrupt();
                            threads.remove(Thread.currentThread());
                        } else {
                            taskRun = tasks.take();
                            System.out.println("взял задачу");
                        }
                    } catch (InterruptedException e) {
                        //e.printStackTrace();
                    }
                }
                 }
                if (taskRun != null) {
                    taskRun.run();
                }
            }
        }
    }
}
