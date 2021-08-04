package com.example.popularmovieapp.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.popularmovieapp.dao.TVShowsDao;
import com.example.popularmovieapp.models.TVShows;

@Database(entities = TVShows.class,version = 2)
public abstract class TVShowsDatabase extends RoomDatabase {

    private static TVShowsDatabase tvShowsDatabase;

    public static synchronized TVShowsDatabase getTvShowsDatabase (Context context)
    {
        if(tvShowsDatabase==null)
        {
            tvShowsDatabase = Room.databaseBuilder(context,TVShowsDatabase.class,"tv_shows_db").fallbackToDestructiveMigration()
                    .build();
        }
        return tvShowsDatabase;
    }

    public abstract TVShowsDao tvShowsDao();

}
