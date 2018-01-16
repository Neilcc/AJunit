package com.zcc.ajunit.demo;

import android.content.Context;
import android.os.Handler;

/**
 * Created by cc on 2018/1/16.
 */

public class TestTarget {

    Context context;

    public TestTarget(Context context) {
        this.context = context.getApplicationContext();
    }

    public void doAsyncJob(final AsyncCallback asyncCallback) {
        ALog.i("doAsyncJob start");
        new Thread() {
            @Override
            public void run() {
                ALog.i("doAsyncJob doing");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                asyncCallback.onAsyncFinish();
                ALog.i("doAsyncJob finish");
            }
        }.start();

    }

    public void doAsyncHandlerJob(final AsyncCallback asyncCallback) {
        ALog.i("asyncJobHandler start");
        final Handler handler = new Handler();
        new Thread() {
            @Override
            public void run() {
                ALog.i("asyncJobHandler doing");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        asyncCallback.onAsyncFinish();
                    }
                });
                ALog.i("asyncJobHandler finish");
            }
        }.start();

    }

    public interface AsyncCallback {
        public void onAsyncFinish();
    }
}
