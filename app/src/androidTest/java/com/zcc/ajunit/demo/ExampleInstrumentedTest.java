package com.zcc.ajunit.demo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.zcc.ajunit.AsyncTestBase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(Parameterized.class)
public class ExampleInstrumentedTest extends AsyncTestBase {

    String paramA = "";
    String paramB = "";
    TestTarget testTarget;

    public ExampleInstrumentedTest(String paramA, String paramB) {
        this.paramA = paramA;
        this.paramB = paramB;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        //测试数据
        return Arrays.asList(new Object[][]{
                {"testParamA", "testParamB"},
                {"tpa1", "tpb1"},

        });
    }

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        Context context = InstrumentationRegistry.getTargetContext();
        testTarget = new TestTarget(context);
    }


    @Test
    public void testAsyncMethod() throws Exception {
        ALog.i("start test async method with param A" + paramA);
        runAsyncTest(new AsyncTestCase() {
            @Override
            public void onExecuting() throws Exception {
                testTarget.doAsyncJob(new TestTarget.AsyncCallback() {
                    @Override
                    public void onAsyncFinish() {
                        onAsyncTestFinished();
                    }
                });
            }
        });

    }

    @Test
    public void testAsyncHandlerMethod() throws Exception {
        ALog.i("start test async handler method with param B" + paramB);
        runAsyncTest(new AsyncTestCase() {
            @Override
            public void onExecuting() throws Exception {
                testTarget.doAsyncHandlerJob(new TestTarget.AsyncCallback() {
                    @Override
                    public void onAsyncFinish() {
                        onAsyncTestFinished();
                    }
                });
            }
        });
    }

    @Test
    public void testAsyncHandlerMethodError() throws Exception {
        ALog.i("start test async handler method with param B" + paramB);
        runAsyncTest(new AsyncTestCase() {
            @Override
            public void onExecuting() throws Exception {
                testTarget.doAsyncHandlerJob(new TestTarget.AsyncCallback() {
                    @Override
                    public void onAsyncFinish() {
                        if (paramB.equals("tpb1")) {
                            Object a = null;
                            a.equals("asd");
                        }
                        onAsyncTestFinished();
                    }
                });
            }
        });
    }
}
