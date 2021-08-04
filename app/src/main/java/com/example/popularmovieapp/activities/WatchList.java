package com.example.popularmovieapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.popularmovieapp.R;
import com.example.popularmovieapp.adapters.WatchListAdapter;
import com.example.popularmovieapp.databinding.ActivityWatchListBinding;
import com.example.popularmovieapp.listeners.WatchListListener;
import com.example.popularmovieapp.models.TVShows;
import com.example.popularmovieapp.models.TVShowsDetails;
import com.example.popularmovieapp.utilities.TempDataHolder;
import com.example.popularmovieapp.viewmodel.TVShowsDetailsViewModel;
import com.example.popularmovieapp.viewmodel.WatchListViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WatchList extends AppCompatActivity implements WatchListListener {
    private ActivityWatchListBinding watchListBinding;
    private WatchListViewModel watchListViewModel;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private WatchListAdapter watchListAdapter;
    private List<TVShows> tvShowsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        watchListBinding = DataBindingUtil.setContentView(this,R.layout.activity_watch_list);

        doInitialize();
        loadWatchList();


    }

    private void doInitialize()
    {
        watchListViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(WatchListViewModel.class);

        // back button
        watchListBinding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tvShowsList = new ArrayList<>();
    }

    private void loadWatchList()
    {
        watchListBinding.setIsLoading(true);
        compositeDisposable.add(
                watchListViewModel.loadWatchList().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(tvShows -> {
                            watchListBinding.setIsLoading(false);
                            if(tvShows.size() > 0)
                            {
                                tvShowsList.clear();
                            }
                            tvShowsList.addAll(tvShows);
                            watchListAdapter = new WatchListAdapter(tvShowsList,this);
                            watchListBinding.watchListRecyclerView.setAdapter(watchListAdapter);
                            watchListBinding.watchListRecyclerView.setVisibility(View.VISIBLE);

                        })
        );
    }

    @Override
    public void onTVShowClicked(TVShows tvShows) {
        Intent intent = new Intent(getBaseContext(), TVShowsDetailsActivity.class);
        intent.putExtra("tvShows",tvShows);
        startActivity(intent);
    }

    @Override
    public void removeTVShowFromWatchList(TVShows tvShows, int position) {
        compositeDisposable.add(
                watchListViewModel.removeTVShowFromWatchList(tvShows).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(()->{
                            tvShowsList.remove(position);
                            watchListAdapter.notifyItemRemoved(position);
                            watchListAdapter.notifyItemRangeChanged(position,watchListAdapter.getItemCount());
                        })
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(TempDataHolder.IS_WATCHLIST_UPDATED)
        {
            loadWatchList();
            TempDataHolder.IS_WATCHLIST_UPDATED = false;
        }
    }
}