package com.blogspot.vsvydenko.flickrgrid.ui;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.blogspot.vsvydenko.flickrgrid.R;
import com.blogspot.vsvydenko.flickrgrid.entity.FlickrImage;
import com.blogspot.vsvydenko.flickrgrid.entity.FlickrImagesResponse;
import com.blogspot.vsvydenko.flickrgrid.network.RequestManager;

import org.json.JSONObject;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vsvydenko on 10.10.14.
 */
public class GridFragment extends Fragment {

    private View returnView;
    private GridView mGridView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private boolean hasMoreItems;
    private boolean mLoading;

    private RecentPhotosAdapter mRecentPhotosAdapter;
    private int page = 1;
    List<FlickrImage> mFlickrImageList;
    private boolean restore;

    public GridFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retain this fragment across configuration changes.
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        returnView = inflater.inflate(R.layout.fragment_grid, container, false);
        return returnView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initializeContent();
    }

    @Override
    public void onPause() {
        restore = true;
        super.onPause();
    }

    private void initializeContent() {

        mGridView = (GridView) returnView.findViewById(R.id.grid);
        mSwipeRefreshLayout = (SwipeRefreshLayout) returnView.findViewById(R.id.swipe_container);

        mSwipeRefreshLayout.setEnabled(false);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        if (restore && mFlickrImageList.size() > 0) {
            updateUI();
            restore = false;
        } else {
            loadPhotos(1);
        }
    }

    private void loadPhotos(int page) {
        mSwipeRefreshLayout.setRefreshing(true);
        mLoading = true;
        recentPhotosRequest(page);
    }

    private void recentPhotosRequest(int page) {
        RequestManager.getInstance().doRequest().flickrRecentPhotos(page, listener, errorListener);
    }

    Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(final JSONObject jsonObject) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (mFlickrImageList == null || page == 1) {
                        mFlickrImageList = new ArrayList<FlickrImage>();
                    }
                    FlickrImagesResponse flickrImagesResponse = FlickrImagesResponse.
                            fromJson(jsonObject);
                    final List<FlickrImage> response = flickrImagesResponse.getFlickrImages();

                    hasMoreItems = flickrImagesResponse.getPages() > page;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mFlickrImageList.addAll(response);
                            updateUI();
                        }
                    });

                    mLoading = false;
                }
            }).start();

        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            mLoading = false;
            Toast.makeText(getActivity(), getActivity().getString(R.string.an_error_occured),
                    Toast.LENGTH_SHORT).show();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    };

    private void updateUI() {
        if (mRecentPhotosAdapter == null) {
            mRecentPhotosAdapter = new RecentPhotosAdapter(getActivity(), mFlickrImageList);
        }
        mSwipeRefreshLayout.setRefreshing(false);
        if (mGridView.getAdapter() == null) {
            mGridView.setAdapter(mRecentPhotosAdapter);
        } else {
            mRecentPhotosAdapter.notifyDataSetChanged();
        }

        mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem,
                    int visibleItemCount, int totalItemCount) {
                if (mLoading || visibleItemCount == 0 || totalItemCount == 0) {
                    return;
                }

                // if at bottom
                if (firstVisibleItem + visibleItemCount >= totalItemCount && hasMoreItems) {
                    loadPhotos(++page);
                }
            }
        });
    }

}
