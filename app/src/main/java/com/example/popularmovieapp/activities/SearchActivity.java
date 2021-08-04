package com.example.popularmovieapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.example.popularmovieapp.R;
import com.example.popularmovieapp.adapters.TVShowAdapter;
import com.example.popularmovieapp.databinding.ActivitySearchBinding;
import com.example.popularmovieapp.listeners.TVShowsListener;
import com.example.popularmovieapp.models.TVShows;
import com.example.popularmovieapp.viewmodel.SearchViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity implements TVShowsListener {

    private ActivitySearchBinding activitySearchBinding;
    private SearchViewModel searchViewModel;
    private List<TVShows> tvShowsList2 = new ArrayList<>();
    private TVShowAdapter tvShowAdapter;
    private int currentPage = 1;
    private int totalAvailablePages = 1;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);

        doIntialization();

    }

    private void doIntialization()
    {
        // back button
        activitySearchBinding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //view model
        searchViewModel =  new ViewModelProvider(this).get(SearchViewModel.class);
        //adapter
        tvShowAdapter = new TVShowAdapter(tvShowsList2,this);
        activitySearchBinding.tvSearchRecyclerView.setAdapter(tvShowAdapter);
        // Edit Text
        activitySearchBinding.inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(timer!=null)
                {
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().trim().isEmpty())
                {
                  timer = new Timer();
                  timer.schedule(new TimerTask() {
                      @Override
                      public void run() {
                          new Handler(Looper.getMainLooper()).post(()->{
                             currentPage = 1;
                             totalAvailablePages = 1;
                             searchTVShow(editable.toString());
                          });
                      }
                  },800);
                }
                else
                    {
                        tvShowAdapter.notifyDataSetChanged();
                        tvShowsList2.clear();
                    }
            }
        });

        activitySearchBinding.tvSearchRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!activitySearchBinding.tvSearchRecyclerView.canScrollVertically(1)) { // !mainBinding.recyclerView.canScrollVertically(1) this means recycler cannot scroll the content vertically
                    if (!activitySearchBinding.inputSearch.getText().toString().trim().isEmpty()) {
                        if (currentPage < totalAvailablePages) {
                            currentPage += 1;
                            searchTVShow(activitySearchBinding.inputSearch.getText().toString());
                        }
                    }
                }
            }
        });
        activitySearchBinding.inputSearch.requestFocus();

    }

    private void searchTVShow(String query)
    {
        loadingProgress();
        searchViewModel.searchTVShow(query,currentPage).observe(this,tvShowsResponse -> {
            loadingProgress();
            if(tvShowsResponse != null)
            {
                totalAvailablePages = tvShowsResponse.getPages();
                if(tvShowsResponse.getTv_shows() != null)
                {
                    int oldCount = tvShowsList2.size();
                    tvShowsList2.addAll(tvShowsResponse.getTv_shows());
                    tvShowAdapter.notifyItemRangeInserted(oldCount,tvShowsList2.size());
                }
            }
        });
    }

    private void loadingProgress()
    {
        if(currentPage==1)
        {
            if(activitySearchBinding.getIsLoading()!=null && activitySearchBinding.getIsLoading())
            {
                activitySearchBinding.setIsLoading(false);
            }
            else
            {
                activitySearchBinding.setIsLoading(true);
            }
        }
        else
        {
            if(activitySearchBinding.getIsLoadingMore()!=null && activitySearchBinding.getIsLoadingMore())
            {
                activitySearchBinding.setIsLoadingMore(false);
            }
            else
            {
                activitySearchBinding.setIsLoadingMore(true);
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