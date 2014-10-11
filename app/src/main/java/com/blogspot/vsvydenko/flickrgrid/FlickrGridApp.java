package com.blogspot.vsvydenko.flickrgrid;

import com.blogspot.vsvydenko.flickrgrid.network.RequestManager;
import com.blogspot.vsvydenko.flickrgrid.util.BlurThreadExecutor;

import android.app.Application;

/**
 * Created by vsvydenko on 10.10.14.
 */
public class FlickrGridApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // RequestManager initialization
        RequestManager.getInstance(getApplicationContext());
        BlurThreadExecutor.getInstance(getApplicationContext());
    }

}
