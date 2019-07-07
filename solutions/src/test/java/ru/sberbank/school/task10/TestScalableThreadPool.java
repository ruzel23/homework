package ru.sberbank.school.task10;

import org.junit.After;
import org.junit.jupiter.api.Assertions;
import org.junit.Before;
import org.junit.Test;

public class TestScalableThreadPool {

    private ThreadPool pool;
    private final Object lock = new Object();
    private volatile int count;

    @Before
    public void initialize() {
        pool = new ScalableThreadPool(3, 5);
        pool.start();
    }

    @Test
    public void checkRepeatStart() {
        Assertions.assertThrows(IllegalStateException.class, () -> pool.start());
    }

    @Test
    public void checkIncrement() throws InterruptedException {
        for (int i = 0; i < 1000000; i++) {
            pool.execute(() -> {
                synchronized (lock) {
                    count++;
                }
            });
        }
        Thread.sleep(1000);
        Assertions.assertEquals(1000000, count);
    }

    @After
    public void stopPool() {
        pool.stopNow();
    }

}

