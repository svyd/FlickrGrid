package com.blogspot.vsvydenko.flickrgrid;

/**
 * Created by vsvydenko on 10.10.14.
 */
public class Constants {

    public static final String LOG_TAG = "FLICKR_RECENTS_TAG";

    private static final String API_KEY = "ae82b00096670798e24ece69f824594d";
    private static final String FORMAT = "json";
    private static final String EXTRAS = "url_m";
    private static final int PER_PAGE = 18;
    private static final String GET_RECENT = "flickr.photos.getRecent";
    private static final String NO_JSON_CALLBACK = "nojsoncallback=1";

    private static final String AMPERSAND = "&";

    private static final String BASE_URL = "https://api.flickr.com/services/rest/";

    public static final String RECENT_PHOTOS_URL = BASE_URL + "?" +
            "method=" + GET_RECENT + AMPERSAND +
            "api_key=" + API_KEY + AMPERSAND +
            "extras=" + EXTRAS + AMPERSAND +
            "per_page=" + PER_PAGE + AMPERSAND +
            "format=" + FORMAT + AMPERSAND +
            NO_JSON_CALLBACK + AMPERSAND +
            "page=";
}
