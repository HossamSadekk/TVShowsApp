package com.example.popularmovieapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.example.popularmovieapp.R;
import com.example.popularmovieapp.adapters.TVShowAdapter;
import com.example.popularmovieapp.databinding.ActivityMainBinding;
import com.example.popularmovieapp.listeners.TVShowsListener;
import com.example.popularmovieapp.models.TVShows;
import com.example.popularmovieapp.viewmodel.MostPopularTVShowsViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TVShowsListener {
    private ActivityMainBinding mainBinding;
    private MostPopularTVShowsViewModel viewModel;
    private TVShowAdapter tvShowAdapter;
    private List<TVShows> tvShowsList = new ArrayList<>();
    private int currentPage = 1;
    private int totalAvailablePages = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        doIntialization();


    }

    private void doIntialization()
    {
        mainBinding.recyclerView.setHasFixedSize(true);
        //VIEW MODE
        viewModel = new ViewModelProvider(this).get(MostPopularTVShowsViewModel.class);
        //provide list to adapter
        tvShowAdapter = new TVShowAdapter(tvShowsList,this);
        //provide adapter to recycler view
        mainBinding.recyclerView.setAdapter(tvShowAdapter);

        // Callback method will be called after the scroll has completed.
        /*
        dx is the amount of horizontal scroll
        dy is the amount of vertical scroll
        */
        mainBinding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!mainBinding.recyclerView.canScrollVertically(1))
                { // !mainBinding.recyclerView.canScrollVertically(1) this means recycler cannot scroll the content vertically
                    if(currentPage < totalAvailablePages)
                    {
                        currentPage+=1;
                        getMostPopularTVShows();
                    }
                }
            }
        });

        // handling watchlist button
        mainBinding.imagewatchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),WatchList.class));
            }
        });

        // handling Search button
        mainBinding.imagesearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),SearchActivity.class));
            }
        });

        //calling movies
        getMostPopularTVShows();
    }

    private void getMostPopularTVShows() {
        loadingProgress();
        viewModel.getMostPopularTVShows(currentPage).observe(this, mostPopularTVResponse -> {
            loadingProgress();

            if(mostPopularTVResponse!=null)
            {
                totalAvailablePages =Integer.parseInt(mostPopularTVResponse.getTotal());
                if(mostPopularTVResponse.getTv_shows()!=null)
                {
                    int positionStart = tvShowsList.size();
                    tvShowsList.addAll(mostPopularTVResponse.getTv_shows());
                    int itemCount = tvShowsList.size() - positionStart;
                    tvShowAdapter.notifyItemRangeInserted(positionStart,itemCount);
                }
            }
        });
    }

    private void loadingProgress()
    {
        if(currentPage==1)
        {
            if(mainBinding.getIsLoading()!=null && mainBinding.getIsLoading())
            {
                mainBinding.setIsLoading(false);
            }
            else
            {
                mainBinding.setIsLoading(true);
            }
        }
        else
        {
            if(mainBinding.getIsLoadingMore()!=null && mainBinding.getIsLoadingMore())
            {
                mainBinding.setIsLoadingMore(false);
            }
            else
            {
                mainBinding.setIsLoadingMore(true);
            }
        }
    }

    @Override
    public void onTVShowClicked(TVShows tvShows) {
        Intent intent = new Intent(getBaseContext(),TVShowsDetailsActivity.class);
        intent.putExtra("tvShows",tvShows); // to send/pass object to activity we should make the class of that obj to implement Serializable
        startActivity(intent);
    }
}