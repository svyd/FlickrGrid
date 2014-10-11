package com.blogspot.vsvydenko.flickrgrid.widget;

import com.android.volley.toolbox.NetworkImageView;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by vsvydenko on 10.10.14.
 */

public class GridImageView extends NetworkImageView {
    public GridImageView(Context context) {
        super(context);
    }

    public GridImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width
    }
}
