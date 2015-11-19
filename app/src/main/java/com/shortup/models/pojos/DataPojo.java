package com.shortup.models.pojos;

/**
 * Created by sourin on 17/11/15.
 */
public class DataPojo {

    private String long_url;
    private String url;
    private String hash;
    private String global_hash;
    private int new_hash;

    public String getLong_url() {
        return long_url;
    }

    public void setLong_url(String long_url) {
        this.long_url = long_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getGlobal_hash() {
        return global_hash;
    }

    public void setGlobal_hash(String global_hash) {
        this.global_hash = global_hash;
    }

    public int getNew_hash() {
        return new_hash;
    }

    public void setNew_hash(int new_hash) {
        this.new_hash = new_hash;
    }

    public DataPojo(String long_url, String url, String hash, String global_hash, int new_hash) {
        this.long_url = long_url;
        this.url = url;
        this.hash = hash;
        this.global_hash = global_hash;
        this.new_hash = new_hash;
    }
}
