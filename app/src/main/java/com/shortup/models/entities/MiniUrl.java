package com.shortup.models.entities;

import java.io.Serializable;

/**
 * Created by sourin on 17/11/15.
 */
public class MiniUrl implements Serializable{

    private String longUrl;
    private String shortUrl;

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public MiniUrl() {
    }

    public MiniUrl(String longUrl, String shortUrl) {
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
    }
}
