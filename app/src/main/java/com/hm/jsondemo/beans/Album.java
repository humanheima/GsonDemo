package com.hm.jsondemo.beans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonAutoDetect
public class Album {

    private String title;
    private String[] links;
    private List<String> songs;
    public Artist artist;

    private Map<String, String> musicians = new HashMap<>();

    public Map<String, String> getMusicians() {
        return Collections.unmodifiableMap(musicians);
    }

    public void addMusician(String key, String value) {
        musicians.put(key, value);
    }

    public Album(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }


    public String[] getLinks() {
        return links;
    }

    public void setLinks(String[] links) {
        this.links = links;
    }

    public List<String> getSongs() {
        return songs;
    }

    public void setSongs(List<String> songs) {
        this.songs = songs;
    }
}
