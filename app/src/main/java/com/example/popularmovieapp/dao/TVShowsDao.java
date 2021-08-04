package com.example.popularmovieapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.popularmovieapp.models.TVShows;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface TVShowsDao {

    @Query("SELECT * FROM tvShows")
    Flowable<List<TVShows>> getWatchList();

    @Query("SELECT * FROM tvShows WHERE id = :tvShowId")
    Flowable<TVShows> getTVShowFromWatchList(String tvShowId);

    @Insert
    Completable addToWatchList(TVShows tvShows);

    @Delete
    Completable deleteFromWatchList(TVShows tvShows);
}
