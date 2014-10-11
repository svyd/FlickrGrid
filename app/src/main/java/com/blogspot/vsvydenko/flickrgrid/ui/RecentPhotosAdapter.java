package com.blogspot.vsvydenko.flickrgrid.ui;

import com.android.volley.toolbox.NetworkImageView;
import com.blogspot.vsvydenko.flickrgrid.R;
import com.blogspot.vsvydenko.flickrgrid.entity.FlickrImage;
import com.blogspot.vsvydenko.flickrgrid.network.ImageManager;
import com.blogspot.vsvydenko.flickrgrid.util.BlurImageTask;
import com.blogspot.vsvydenko.flickrgrid.util.BlurThreadExecutor;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by vsvydenko on 10.10.14.
 */
public class RecentPhotosAdapter extends BaseAdapter {

    private Context mContext;
    private List<FlickrImage> mItems;

    public RecentPhotosAdapter(Context mContext, List<FlickrImage> items) {
        this.mContext = mContext;
        this.mItems = items;
    }


    @Override
    public int getCount() {
        return mItems != null ? mItems.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.recent_photo_item, parent, false);
        }

        NetworkImageView flickrPhoto = ViewHolder.get(convertView, R.id.imgPhoto);
        TextView title = ViewHolder.get(convertView, R.id.txtTitle);

        FlickrImage flickrImage = (FlickrImage) getItem(position);
        flickrPhoto.setImageUrl(flickrImage.getUrl(), ImageManager.loader());
        title.setText(flickrImage.getTitle());
        applyBlur(flickrPhoto, title);
        return convertView;
    }

    public static class ViewHolder {

        // I added a generic return type to reduce the casting noise in client code
        @SuppressWarnings("unchecked")
        public static <T extends View> T get(View view, int id) {
            SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
            if (viewHolder == null) {
                viewHolder = new SparseArray<View>();
                view.setTag(viewHolder);
            }
            View childView = viewHolder.get(id);
            if (childView == null) {
                childView = view.findViewById(id);
                viewHolder.put(id, childView);
            }
            return (T) childView;
        }
    }

    private void applyBlur(final NetworkImageView networkImageView, final TextView textView) {
        networkImageView.getViewTreeObserver()
                .addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        networkImageView.getViewTreeObserver().removeOnPreDrawListener(this);
                        networkImageView.buildDrawingCache();

                        Bitmap bmp = networkImageView.getDrawingCache();
                        BlurThreadExecutor.getInstance().executor()
                                .execute(new BlurImageTask(mContext, bmp, textView));
                        return true;
                    }
                });
    }
}
