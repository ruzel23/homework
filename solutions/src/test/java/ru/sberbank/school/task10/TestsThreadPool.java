package ru.sberbank.school.task10;

import org.junit.jupiter.api.Assertions;

import java.util.HashMap;
import java.util.Map;

class TestsThreadPool {

    private final Object lock = new Object();
    private int count;
    private Map<Thread, Integer> countMap;

    TestsThreadPool() {
        countMap = new HashMap<>();
    }

    void checkRepeatStart(ThreadPool pool) {
        Assertions.assertThrows(IllegalStateException.class, pool::start);
    }

    void checkIncrement(ThreadPool pool) throws InterruptedException {
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

    public void checkAdequacy() throws InterruptedException {

    }

}
