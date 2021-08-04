package com.example.popularmovieapp.responses;

import com.example.popularmovieapp.models.TVShowsDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TVShowsDetailsResponse {

    @Expose
    @SerializedName("tvShow")
    private TVShowsDetails tvShowDetails;

    public TVShowsDetails getTvShowsDetails() {
        return tvShowDetails;
    }


}
