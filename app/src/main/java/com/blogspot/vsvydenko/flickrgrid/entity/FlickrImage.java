package com.blogspot.vsvydenko.flickrgrid.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vsvydenko on 10.10.14.
 */
public class FlickrImage {

    private String title;
    private String url;

    public FlickrImage(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static FlickrImage fromJson(JSONObject jsonObject) {

        FlickrImage flickrImage = new FlickrImage();

        try {
            flickrImage.setTitle(jsonObject.getString("title"));
            flickrImage.setUrl(jsonObject.getString("url_m"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return flickrImage;
    }

}
