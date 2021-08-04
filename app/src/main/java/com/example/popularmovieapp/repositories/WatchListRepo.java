package com.example.popularmovieapp.repositories;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.popularmovieapp.databases.TVShowsDatabase;
import com.example.popularmovieapp.models.TVShows;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class WatchListRepo {
    private TVShowsDatabase tvShowsDatabase;

    public WatchListRepo(@NonNull Application application) {
        tvShowsDatabase = TVShowsDatabase.getTvShowsDatabase(application);
    }

    public Flowable<List<TVShows>> loadWatchList()
    {
        return tvShowsDatabase.tvShowsDao().getWatchList();
    }

    public Completable removeTVShowFromWatchList(TVShows tvShows)
    {
        return tvShowsDatabase.tvShowsDao().deleteFromWatchList(tvShows);
    }


}
