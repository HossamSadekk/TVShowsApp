package com.example.popularmovieapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TVShowsDetails {

        @Expose
        @SerializedName("episodes")
        private List<Episode> episodes;
        @Expose
        @SerializedName("pictures")
        private List<String> pictures;
        @Expose
        @SerializedName("genres")
        private List<String> genres;
        @Expose
        @SerializedName("rating")
        private String rating;
        @Expose
        @SerializedName("image_thumbnail_path")
        private String image_thumbnail_path;
        @Expose
        @SerializedName("runtime")
        private int runtime;
        @Expose
        @SerializedName("description")
        private String description;
        @Expose
        @SerializedName("url")
        private String url;

        public List<Episode> getEpisodes() {
            return episodes;
        }

        public List<String> getPictures() {
            return pictures;
        }

        public List<String> getGenres() {
            return genres;
        }

        public String getRating() {
            return rating;
        }

        public String getImage_thumbnail_path() {
            return image_thumbnail_path;
        }

        public int getRuntime() {
            return runtime;
        }

        public String getDescription() {
            return description;
        }

        public String getUrl() {
            return url;
        }
    }



