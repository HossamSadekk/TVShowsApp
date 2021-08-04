package com.example.popularmovieapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.popularmovieapp.repositories.SearchTVShowRepository;
import com.example.popularmovieapp.responses.TVShowsResponse;

public class SearchViewModel extends ViewModel {
    private SearchTVShowRepository searchTVShowRepository;

    public SearchViewModel() {
        searchTVShowRepository = new SearchTVShowRepository();
    }

    public LiveData<TVShowsResponse> searchTVShow(String query,int page)
    {
        return searchTVShowRepository.searchTVShow(query, page);
    }
}
