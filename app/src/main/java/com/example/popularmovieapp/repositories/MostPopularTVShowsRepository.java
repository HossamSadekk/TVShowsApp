package com.example.popularmovieapp.repositories;

import android.util.Log;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.popularmovieapp.network.ApiClient;
import com.example.popularmovieapp.network.ApiService;
import com.example.popularmovieapp.responses.TVShowsResponse;


import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MostPopularTVShowsRepository {
    private static final String TAG = "MostPopularTVShowsRepos";
    private ApiService apiService;

    public MostPopularTVShowsRepository()
    {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

 /*   public LiveData<TVShowsResponse> getMostPopularTVShow(int page)
    {
        MutableLiveData<TVShowsResponse> data = new MutableLiveData<>();
        apiService.getMostPopularTVShows(page).enqueue(new Callback<TVShowsResponse>() {
            @Override
            public void onResponse(@NonNull Call<TVShowsResponse> call,@NonNull Response<TVShowsResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TVShowsResponse> call,@NonNull Throwable t) {
                Log.d("f","Failed"+t.getMessage());
            }
        });
        return data;
    }*/

    public LiveData<TVShowsResponse> getMostPopularTVShows(int page)
    {
        MutableLiveData<TVShowsResponse> data = new MutableLiveData<>();

        Single<TVShowsResponse> observable =apiService.getMostPopularTVShows(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        SingleObserver<TVShowsResponse> observer = new SingleObserver<TVShowsResponse>() {
            @Override
            public void onSubscribe(Disposable disposable) {

            }

            @Override
            public void onSuccess(TVShowsResponse tvShowsResponse) {
                data.setValue(tvShowsResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                Log.d(TAG,"error"+throwable);
            }
        };

        observable.subscribe(observer);

        return data;
    }
}
