package com.example.popularmovieapp.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.popularmovieapp.network.ApiClient;
import com.example.popularmovieapp.network.ApiService;
import com.example.popularmovieapp.responses.TVShowsResponse;

import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchTVShowRepository {
    private static final String TAG = "SearchTVShowRepository";
    private ApiService apiService;

    public SearchTVShowRepository() {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<TVShowsResponse> searchTVShow(String query,int page)
    {
        MutableLiveData<TVShowsResponse> data = new MutableLiveData<>();
        apiService.searchTVShow(query,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<TVShowsResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(TVShowsResponse tvShowsResponse) {
                        data.setValue(tvShowsResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG,"error-> "+e);
                    }
                });
        return data;
    }

}
