package com.blogspot.vsvydenko.flickrgrid.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;

/**
 * Created by vsvydenko on 10.10.14.
 */
public class BlurImageTask implements Runnable {

    private Bitmap mBitmap;
    private View mView;
    private Context mContext;

    public BlurImageTask(Context context, Bitmap bitmap, View view) {
        mContext = context;
        mBitmap = bitmap;
        mView = view;
    }

    @Override
    public void run() {

        float radius = 20;
        final Bitmap overlay = Bitmap.createBitmap(mView.getMeasuredWidth(),
                mView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.translate(-mView.getLeft(), -mView.getTop());
        canvas.drawBitmap(mBitmap, 0, 0, null);
        RenderScript rs = RenderScript.create(mContext);
        Allocation overlayAlloc = Allocation.createFromBitmap(
                rs, overlay);
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(
                rs, overlayAlloc.getElement());
        blur.setInput(overlayAlloc);
        blur.setRadius(radius);
        blur.forEach(overlayAlloc);
        overlayAlloc.copyTo(overlay);
        mView.post(new Runnable() {
            @Override
            public void run() {
                mView.setBackground(new BitmapDrawable(
                        mContext.getResources(), overlay));
                mView.invalidate();
            }
        });
        rs.destroy();

    }
}
