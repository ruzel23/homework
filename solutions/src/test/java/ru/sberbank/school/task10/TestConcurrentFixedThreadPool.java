package ru.sberbank.school.task10;

import org.junit.Before;

public class TestConcurrentFixedThreadPool extends TestFixedThreadPool {

    private ConcurrentFixedThreadPool pool;
    private final Object lock = new Object();
    private int count;
    private int thread_1;
    private int thread_2;
    private int thread_3;

    @Before
    public void initialize() {
        pool = new ConcurrentFixedThreadPool(3);
        pool.start();
    }



}
