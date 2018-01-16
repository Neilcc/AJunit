package com.zcc.ajunit;

import android.os.HandlerThread;

/**
 * Created by cc on 2018/1/10.
 */

public class AsyncTestThread extends HandlerThread {
    protected OnThrowableListener throwableListener;

    public AsyncTestThread(String name) {
        super(name);
    }

    public AsyncTestThread(String name, int priority) {
        super(name, priority);
    }

    public void setThrowableListener(OnThrowableListener throwableListener) {
        this.throwableListener = throwableListener;
    }

    @Override
    public void run() {
        try {
            super.run();
        } catch (Throwable throwable) {
            if (this.throwableListener != null) {
                throwableListener.onThrowable(throwable);
            }
        }
    }

    public interface OnThrowableListener {
        void onThrowable(Throwable throwable);
    }
}
