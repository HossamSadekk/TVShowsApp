package com.example.popularmovieapp.viewmodel;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.popularmovieapp.models.TVShows;
import com.example.popularmovieapp.repositories.TVShowsDetailsRepository;
import com.example.popularmovieapp.responses.TVShowsDetailsResponse;

import io.reactivex.Completable;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;


public class TVShowsDetailsViewModel extends AndroidViewModel {
    private TVShowsDetailsRepository tvShowsDetailsRepository;



    public TVShowsDetailsViewModel(Application application)
    {
        super(application);
        tvShowsDetailsRepository = new TVShowsDetailsRepository(application);
    }

    public LiveData<TVShowsDetailsResponse> getTVShowsDetails(String tvShowId)
    {
        return tvShowsDetailsRepository.getTVShowsDetails(tvShowId);
    }

    public Completable addToWatchList(TVShows tvShows)
    {
        return tvShowsDetailsRepository.addToWatchList(tvShows);
    }

    public Flowable<TVShows> getTVShowFromWatchList(String tvShowId)
    {
        return tvShowsDetailsRepository.getTVShowFromWatchList(tvShowId);
    }

    public Completable removeTVShowFromWatchList(TVShows tvShows)
    {
        return tvShowsDetailsRepository.removeTVShowFromWatchList(tvShows);
    }

}
