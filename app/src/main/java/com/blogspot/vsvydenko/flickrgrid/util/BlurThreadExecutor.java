package com.blogspot.vsvydenko.flickrgrid.util;

import android.content.Context;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by vsvydenko on 10.10.14.
 */
public class BlurThreadExecutor {

    private static BlurThreadExecutor mInstance;
    private static Context mContext;

    /*
     * Gets the number of available cores
     * (not always the same as the maximum number of cores)
     */
    private static int NUMBER_OF_CORES =
            Runtime.getRuntime().availableProcessors();

    // A queue of Runnables
    private final BlockingQueue<Runnable> mBlurWorkQueue = new LinkedBlockingQueue<Runnable>();

    // Sets the amount of time an idle thread waits before terminating
    private static final int KEEP_ALIVE_TIME = 8;
    // Sets the Time Unit to seconds
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

    ThreadPoolExecutor blurThreadExecutor;

    private BlurThreadExecutor(){

        // Creates a thread pool manager
        blurThreadExecutor = new ThreadPoolExecutor(
                NUMBER_OF_CORES,       // Initial pool size
                NUMBER_OF_CORES,       // Max pool size
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                mBlurWorkQueue);
    }

    // This method should be called first to do singleton initialization
    public static synchronized BlurThreadExecutor getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new BlurThreadExecutor();
            mContext = context;
        }
        return mInstance;
    }

    public static synchronized BlurThreadExecutor getInstance() {
        if (mInstance == null) {
            throw new IllegalStateException(BlurThreadExecutor.class.getSimpleName() +
                    " is not initialized, call getInstance(..) method first.");
        }
        return mInstance;
    }

    public ThreadPoolExecutor executor() {
        return blurThreadExecutor;
    }
}
