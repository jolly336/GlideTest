package com.nelson.glidetest.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Process;
import android.support.annotation.NonNull;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A thread tools that manages {@link Runnable} execution by {@link ThreadPoolExecutor} and
 * {@link HandlerThread}.
 *
 * Created by Nelson on 2018/1/25.
 */

public final class ThreadManager {

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE = 1;

    private static final Handler MAIN_THREAD_HANDLER = new Handler(Looper.getMainLooper());

    private static final Executor EXECUTOR = new ThreadPoolExecutor(0, MAXIMUM_POOL_SIZE,
            KEEP_ALIVE, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(64),
            new DefaultThreadFactory());

    private static final HandlerThread HANDLER_THREAD = new HandlerThread("background thread");
    private static final Handler DELIVER;

    static {
        HANDLER_THREAD.start();
        DELIVER = new Handler(HANDLER_THREAD.getLooper());
    }

    private ThreadManager() {
    }

    public final static void runOnThreadPool(Runnable runnable) {
        EXECUTOR.execute(runnable);
    }

    public final static void runOnMainThread(Runnable runnable) {
        MAIN_THREAD_HANDLER.post(runnable);
    }

    public final static void runOnMainThreadWithDelay(Runnable runnable, long delayMillis) {
        MAIN_THREAD_HANDLER.postDelayed(runnable, delayMillis);
    }

    public final static void runOnBackgroundThread(Runnable runnable) {
        DELIVER.post(runnable);
    }

    public final static void runOnBackgroundTheadWithDelay(Runnable runnable, long delayMillis) {
        DELIVER.postDelayed(runnable, delayMillis);
    }

    public final static void removeAllMessages() {
        DELIVER.removeCallbacksAndMessages(null);
    }

    public final static boolean isOnMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public final static boolean isOnBackgroundThread() {
        return !isOnMainThread();
    }

    public final static void assertMainThread() {
        if (!isOnMainThread()) {
            throw new IllegalArgumentException("You must call this method on the main thread!");
        }
    }

    public final static void assertBackgroundThread() {
        if (!isOnBackgroundThread()) {
            throw new IllegalArgumentException("You must call this method on a background thread!");
        }
    }

    public static class DefaultThreadFactory implements ThreadFactory {

        private final AtomicInteger mThreadNum = new AtomicInteger(1);

        @Override
        public Thread newThread(@NonNull Runnable runnable) {
            Thread result = new Thread(runnable, "thread-pool-" + mThreadNum.getAndIncrement()) {
                @Override
                public void run() {
                    Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                    super.run();
                }
            };
            return result;
        }
    }


}
