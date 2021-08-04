package com.example.popularmovieapp.listeners;

import com.example.popularmovieapp.models.TVShows;

public interface WatchListListener {
    void onTVShowClicked(TVShows tvShows);
    void removeTVShowFromWatchList(TVShows tvShows , int position);
}
