package com.example.popularmovieapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.popularmovieapp.databases.TVShowsDatabase;
import com.example.popularmovieapp.models.TVShows;
import com.example.popularmovieapp.repositories.WatchListRepo;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class WatchListViewModel extends AndroidViewModel {
    private WatchListRepo watchListRepo;

    public WatchListViewModel(@NonNull Application application) {
        super(application);
        watchListRepo = new WatchListRepo(application);
    }

    public Flowable<List<TVShows>> loadWatchList()
    {
        return watchListRepo.loadWatchList();
    }

    public Completable removeTVShowFromWatchList(TVShows tvShows)
    {
        return watchListRepo.removeTVShowFromWatchList(tvShows);
    }
}
