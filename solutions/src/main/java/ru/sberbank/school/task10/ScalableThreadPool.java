package ru.sberbank.school.task10;

import java.util.*;

public class ScalableThreadPool implements ThreadPool {

    private int minSize;
    private int maxSize;
    private Deque<Thread> threads;
    private Queue<Runnable> tasks;

    ScalableThreadPool(int minSize, int maxSize) {
        this.minSize = minSize;
        this.maxSize = maxSize;
        threads = new LinkedList<>();
        tasks = new LinkedList<>();
    }

    @Override
    public void start() {
        if (!threads.isEmpty()) {
            throw new IllegalStateException("старт уже был произведен");
        }
        for (int i = 0; i < minSize; i++) {
            threads.addLast(new ThreadWorker("ThreadPoolWorker-" + i));
            threads.getLast().start();
        }
    }

    @Override
    public void stopNow() {
        synchronized (tasks) {
            tasks.clear();
            for (Thread thread : threads) {
                thread.interrupt();
            }
            threads.clear();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        synchronized (tasks) {
            synchronized (threads) {
                if (!(tasks.isEmpty()) && (threads.size() > minSize && threads.size() < maxSize)) {
                    threads.addLast(new ThreadWorker("ThreadPoolWorker-" + (threads.size() - 1)));
                    threads.getLast().start();
                }
                tasks.add(runnable);
                tasks.notify();
            }
        }
    }

    private class ThreadWorker extends Thread {

        ThreadWorker(String name) {
            super(name);
        }

        @Override
        public void run() {
            Runnable taskRun;

            synchronized (tasks) {
                while (!Thread.currentThread().isInterrupted()) {
                    if (tasks.isEmpty()) {
                        synchronized (threads) {
                            try {
                                if (threads.size() > minSize) {
                                    Thread.currentThread().interrupt();
                                }
                                tasks.wait();
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
                taskRun = tasks.poll();
            }
            if (taskRun != null) {
                taskRun.run();
            }
        }
    }
}
