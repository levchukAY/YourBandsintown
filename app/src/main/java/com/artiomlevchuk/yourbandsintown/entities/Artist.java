package com.artiomlevchuk.yourbandsintown.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Artist {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("facebook_page_url")
    @Expose
    private String facebookPageUrl;
    @SerializedName("tracker_count")
    @Expose
    private int trackerCount;
    @SerializedName("upcoming_event_count")
    @Expose
    private int upcomingEventCount;

    public Artist(String name, String url, String facebookPageUrl,
                  int trackerCount, int upcomingEventCount) {
        this.name = name;
        this.url = url;
        this.facebookPageUrl = facebookPageUrl;
        this.trackerCount = trackerCount;
        this.upcomingEventCount = upcomingEventCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getFacebookPageUrl() {
        return facebookPageUrl;
    }
    public int getTrackerCount() {
        return trackerCount;
    }

    public int getUpcomingEventCount() {
        return upcomingEventCount;
    }

}