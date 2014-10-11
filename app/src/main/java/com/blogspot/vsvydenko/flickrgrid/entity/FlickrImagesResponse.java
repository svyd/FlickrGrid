package com.blogspot.vsvydenko.flickrgrid.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vsvydenko on 10.10.14.
 */
public class FlickrImagesResponse {

    private int pages;
    private List<FlickrImage> mFlickrImages = new ArrayList<FlickrImage>();

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<FlickrImage> getFlickrImages() {
        return mFlickrImages;
    }

    public FlickrImagesResponse(){}

    public static FlickrImagesResponse fromJson(JSONObject jsonObject) {

        FlickrImagesResponse flickrImagesResponse = new FlickrImagesResponse();

        try {
            JSONObject flickrResponse = jsonObject.getJSONObject("photos");
            flickrImagesResponse.setPages(flickrResponse.getInt("pages"));
            JSONArray flickrImages = flickrResponse.getJSONArray("photo");
            for (int i = 0; i < flickrImages.length(); i++) {
                JSONObject photo = flickrImages.getJSONObject(i);
                flickrImagesResponse.getFlickrImages().add(FlickrImage.fromJson(photo));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return flickrImagesResponse;
    }

}
