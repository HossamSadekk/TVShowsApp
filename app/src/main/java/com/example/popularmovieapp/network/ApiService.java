package com.example.popularmovieapp.network;

import com.example.popularmovieapp.responses.TVShowsDetailsResponse;
import com.example.popularmovieapp.responses.TVShowsResponse;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("most-popular")
    Single<TVShowsResponse> getMostPopularTVShows(@Query("page") int page);

    @GET("show-details")
    Single<TVShowsDetailsResponse> getTVShowDetails(@Query("q") String tvShowId);


    @GET("search")
    Single<TVShowsResponse> searchTVShow(@Query("q") String query,@Query("page") int page);

}
