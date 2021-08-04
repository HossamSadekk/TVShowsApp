package com.example.popularmovieapp.responses;

import com.example.popularmovieapp.models.TVShows;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TVShowsResponse {

    @Expose
    @SerializedName("tv_shows")
    private List<TVShows> tv_shows;
    @Expose
    @SerializedName("pages")
    private int pages;
    @Expose
    @SerializedName("page")
    private int page;
    @Expose
    @SerializedName("total")
    private String total;

    public List<TVShows> getTv_shows() {
        return tv_shows;
    }

    public int getPages() {
        return pages;
    }

    public int getPage() {
        return page;
    }

    public String getTotal() {
        return total;
    }


}
