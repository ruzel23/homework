package ru.sberbank.school.task10;

import org.junit.After;
import org.junit.jupiter.api.Assertions;
import org.junit.Before;
import org.junit.Test;


public class TestFixedThreadPool extends TestsThreadPool {

    private ThreadPool pool;
    private final Object lock = new Object();
    private int count;
    private int countThread_1;
    private int countThread_2;
    private int countThread_3;

    @Before
    public void initialize() {
        pool = new ConcurrentFixedThreadPool(3);
        pool.start();
    }

    @Test
    public void checkRepeatStart() {
        super.checkRepeatStart(pool);
    }

    @Test
    public void checkIncrement() throws InterruptedException {
        super.checkIncrement(pool);
    }

    @Test
    public void checkAdequacy() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            pool.execute(() -> {
                synchronized (lock) {
                    if (Thread.currentThread().getName().equals("ThreadPoolWorker-0")) {
                        countThread_1++;
                    }
                    if (Thread.currentThread().getName().equals("ThreadPoolWorker-1")) {
                        countThread_2++;
                    }
                    if (Thread.currentThread().getName().equals("ThreadPoolWorker-2")) {
                        countThread_3++;
                    }
                }
            });
        }
        Thread.sleep(10000);
        System.out.println(countThread_1);
        System.out.println(countThread_2);
        System.out.println(countThread_3);

        Assertions.assertEquals(100, countThread_1 + countThread_2 + countThread_3);
    }

    @After
    public void stopPool() {
        pool.stopNow();
    }


}
