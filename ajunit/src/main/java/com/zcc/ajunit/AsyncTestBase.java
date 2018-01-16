package com.zcc.ajunit;

import android.os.Handler;

import org.junit.After;
import org.junit.Before;

import java.util.concurrent.CountDownLatch;

/**
 * Created by cc on 2018/1/10.
 */

public abstract class AsyncTestBase {

    protected AsyncTestThread asyncThread;
    protected Handler handler;
    protected CountDownLatch countDownLatch;
    private AsyncTestException asyncThreadException;

    @Before
    public void setUp() throws Exception {
        asyncThread = new AsyncTestThread(this.getClass().getName());
        asyncThread.start();
        asyncThread.setThrowableListener(new AsyncTestThread.OnThrowableListener() {
            @Override
            public void onThrowable(Throwable throwable) {
                asyncThreadException = AsyncTestException.getException(throwable);
                if (countDownLatch != null && countDownLatch.getCount() > 0)
                    countDownLatch.countDown();
            }
        });
        handler = new Handler(asyncThread.getLooper());
    }

    public void runAsyncTest(final AsyncTestCase asyncTestCase) throws Exception {
        if (asyncTestCase == null) return;
        countDownLatch = new CountDownLatch(1);
        asyncThreadException = null;
        handler.post(asyncTestCase);
        if (countDownLatch != null) {
            countDownLatch.await();
        }
        if (asyncThreadException != null) {
            throw asyncThreadException;
        }
    }

    @After
    public void tearDown() throws Exception {
        if (asyncThread != null && asyncThread.isAlive()) {
            asyncThread.quitSafely();
            asyncThread = null;
        }
        countDownLatch = null;
    }

    public void onAsyncTestFinished() {
        if (countDownLatch != null && countDownLatch.getCount() > 0)
            countDownLatch.countDown();
    }

    public abstract class AsyncTestCase implements Runnable {

        public abstract void onExecuting() throws Exception;

        private void beforeRun() {
        }

        private void afterRun() {
        }

        @Override
        public void run() {
            beforeRun();
            try {
                onExecuting();
            } catch (Throwable e) {
                asyncThreadException = AsyncTestException.getException(e);
            }
            afterRun();
        }
    }

}
