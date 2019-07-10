package ru.sberbank.school.task10;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestConcurrentScalableThreadPool extends TestsThreadPool {

    private ThreadPool pool;

    @Before
    public void initialize() {
        pool = new ConcurrentScalableThreadPool(3, 5);
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
        super.checkAdequacy(pool, 0, 2000);
    }

    @After
    public void stopPool() {
        pool.stopNow();
    }

}