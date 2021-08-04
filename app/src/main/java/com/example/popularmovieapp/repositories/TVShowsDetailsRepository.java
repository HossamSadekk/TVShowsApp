package com.example.popularmovieapp.repositories;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.popularmovieapp.databases.TVShowsDatabase;
import com.example.popularmovieapp.models.TVShows;
import com.example.popularmovieapp.network.ApiClient;
import com.example.popularmovieapp.network.ApiService;
import com.example.popularmovieapp.responses.TVShowsDetailsResponse;


import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class TVShowsDetailsRepository {
    private static final String TAG = "TVShowsDetails";
    private ApiService apiService;
    private TVShowsDatabase tvShowsDatabase;



    public TVShowsDetailsRepository(@NonNull Application application) {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
        tvShowsDatabase = TVShowsDatabase.getTvShowsDatabase(application);
    }

    public LiveData<TVShowsDetailsResponse> getTVShowsDetails(String tvShowId) {
        MutableLiveData<TVShowsDetailsResponse> dataa = new MutableLiveData<>();

        Single<TVShowsDetailsResponse> observable = apiService.getTVShowDetails(tvShowId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        SingleObserver<TVShowsDetailsResponse> observer = new SingleObserver<TVShowsDetailsResponse>() {
            @Override
            public void onSubscribe(Disposable disposable) {

            }

            @Override
            public void onSuccess(TVShowsDetailsResponse tvShowsResponse) {
                dataa.setValue(tvShowsResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                Log.d(TAG, "error in details a7a " + throwable);
            }
        };

        observable.subscribe(observer);

        return dataa;
    }


    public Completable addToWatchList(TVShows tvShows)
    {
        return tvShowsDatabase.tvShowsDao().addToWatchList(tvShows);
    }

    public Flowable<TVShows> getTVShowFromWatchList(String tvShowId)
    {
       return tvShowsDatabase.tvShowsDao().getTVShowFromWatchList(tvShowId);
    }

    public Completable removeTVShowFromWatchList(TVShows tvShows)
    {
        return tvShowsDatabase.tvShowsDao().deleteFromWatchList(tvShows);
    }



}