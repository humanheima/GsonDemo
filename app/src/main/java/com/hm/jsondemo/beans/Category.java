package com.hm.jsondemo.beans;

import android.util.Log;

/**
 * Created by dumingwei on 2020/5/23.
 * <p>
 * Desc:
 */
public class Category {

    private static final String TAG = "Category";
    private String contentType;
    private String coverPath;
    private int id;
    private String title;
    private String url = "http://baidu.com";


    public Category() {
        Log.d(TAG, "Category: no argus construct");

    }

    public Category(String contentType, String coverPath, int id, String title, String url) {
        Log.d(TAG, "Category: construct");
        this.contentType = contentType;
        this.coverPath = coverPath;
        this.id = id;
        this.title = title;
        this.url = url;


    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "Category{" +
                "contentType='" + contentType + '\'' +
                ", coverPath='" + coverPath + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
