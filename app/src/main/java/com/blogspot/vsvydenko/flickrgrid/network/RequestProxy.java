package com.blogspot.vsvydenko.flickrgrid.network;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.blogspot.vsvydenko.flickrgrid.Constants;

import android.content.Context;

import java.io.File;

/**
 * Created by vsvydenko on 29.06.14.
 */
public class RequestProxy {

    private RequestQueue mRequestQueue;
    private RequestQueue mImageLoaderQueue;

    // Default maximum disk usage in bytes
    private static final int DEFAULT_DISK_USAGE_BYTES = 5 * 1024 * 1024;

    // Default cache folder name
    private static final String DEFAULT_CACHE_DIR = "flickr_recent_photos";

    // package access constructor
    RequestProxy(Context context) {
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        mImageLoaderQueue = newPhotosRequestQueue(context.getApplicationContext());

        ImageManager.initializeWith(context.getApplicationContext(), mImageLoaderQueue);
    }



    // Most code copied from "Volley.newRequestQueue(..)", we only changed cache directory
    private static RequestQueue newPhotosRequestQueue(Context context) {
        // define cache folder
        File rootCache = context.getExternalCacheDir();
        if (rootCache == null) {
            android.util.Log.w(Constants.LOG_TAG, "Can't find External Cache Dir, "
                    + "switching to application specific cache directory");
            rootCache = context.getCacheDir();
        }

        File cacheDir = new File(rootCache, DEFAULT_CACHE_DIR);
        cacheDir.mkdirs();

        HttpStack stack = new HurlStack();
        Network network = new BasicNetwork(stack);
        DiskBasedCache diskBasedCache = new DiskBasedCache(cacheDir, DEFAULT_DISK_USAGE_BYTES);
        RequestQueue queue = new RequestQueue(diskBasedCache, network);
        queue.start();

        return queue;
    }

    public void flickrRecentPhotos(int page,
            Response.Listener listener, Response.ErrorListener errorListener) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Constants.RECENT_PHOTOS_URL  + page,
                null,
                listener,
                errorListener
        );
        jsonObjectRequest.setRetryPolicy(setTimeoutPolicy(15000));
        mRequestQueue.add(jsonObjectRequest);
    }

    private RetryPolicy setTimeoutPolicy(int timeInMilisec) {

        return new DefaultRetryPolicy(timeInMilisec,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    }

}
