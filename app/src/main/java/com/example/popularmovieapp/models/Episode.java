package com.example.popularmovieapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Episode {

    @Expose
    @SerializedName("air_date")
    private String air_date;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("episode")
    private int episode;
    @Expose
    @SerializedName("season")
    private int season;

    public String getAir_date() {
        return air_date;
    }

    public String getNameEpisode() {
        return name;
    }

    public int getEpisode() {
        return episode;
    }

    public int getSeason() {
        return season;
    }
}
