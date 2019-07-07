package ru.sberbank.school.task10;

import java.util.*;

public class ScalableThreadPool implements ThreadPool {

    private int minSize;
    private int maxSize;
    private ArrayList<Thread> threads;
    private Queue<Runnable> tasks;

    ScalableThreadPool(int minSize, int maxSize) {
        this.minSize = minSize;
        this.maxSize = maxSize;
        threads = new ArrayList<>();
        tasks = new LinkedList<>();
    }

    @Override
    public void start() {
        if (!threads.isEmpty()) {
            throw new IllegalStateException("старт уже был произведен");
        }
        for (int i = 0; i < minSize; i++) {
            threads.add(new ThreadWorker("ThreadPoolWorker-" + i));
            threads.get(i).start();
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
                if (!(tasks.isEmpty()) && threads.size() >= minSize && threads.size() < maxSize) {
                    ThreadWorker threadWorker = new ThreadWorker("ThreadPoolWorker-" + threads.size());
                    threads.add(threadWorker);
                    threadWorker.start();
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
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (tasks) {
                    if (tasks.isEmpty()) {
                        try {
                            if (threads.size() > minSize) {
                                Thread.currentThread().interrupt();
                                threads.remove(Thread.currentThread());
                            } else {
                                tasks.wait();
                            }
                        } catch (InterruptedException ex) {

                        }
                    }
                    taskRun = tasks.poll();
                }
                if (taskRun != null) {
                    System.out.println(Thread.currentThread().getName());
                    taskRun.run();
                }
            }
        }
    }
}
