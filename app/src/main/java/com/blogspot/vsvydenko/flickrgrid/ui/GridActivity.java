package com.blogspot.vsvydenko.flickrgrid.ui;

import com.blogspot.vsvydenko.flickrgrid.R;

import android.app.Activity;
import android.os.Bundle;



public class GridActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new GridFragment())
                    .commit();
        }
    }

}
