package com.example.popularmovieapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.popularmovieapp.R;
import com.example.popularmovieapp.adapters.EpisodesAdapter;
import com.example.popularmovieapp.adapters.ImageSliderAdapter;
import com.example.popularmovieapp.databinding.ActivityTVShowsDetailsBinding;
import com.example.popularmovieapp.databinding.LayoutButtomSheetBinding;
import com.example.popularmovieapp.models.TVShows;
import com.example.popularmovieapp.models.TVShowsDetails;
import com.example.popularmovieapp.utilities.TempDataHolder;
import com.example.popularmovieapp.viewmodel.TVShowsDetailsViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TVShowsDetailsActivity extends AppCompatActivity {
    private ActivityTVShowsDetailsBinding tvShowsDetailsBinding;
    private TVShowsDetailsViewModel tvShowsDetailsViewModel;
    private BottomSheetDialog bottomSheetDialog;
    private LayoutButtomSheetBinding layoutButtomSheetBinding;
    private TVShows tvShows;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Boolean isTVShowAvilableInWatchList = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvShowsDetailsBinding = DataBindingUtil.setContentView(this,R.layout.activity_t_v_shows_details);

        doIntialization();

        // back button
        tvShowsDetailsBinding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void doIntialization()
    {
        tvShowsDetailsViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(TVShowsDetailsViewModel.class);
        tvShows = (TVShows) getIntent().getSerializableExtra("tvShows");
        checkTVShowInWatchList();
        getTVShowDeltails();
    }

    private void checkTVShowInWatchList()
    {
        compositeDisposable.add(
                tvShowsDetailsViewModel.getTVShowFromWatchList(String.valueOf(tvShows.getId()))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(tvShows -> {
                            isTVShowAvilableInWatchList = true;
                            tvShowsDetailsBinding.imagewatchlist.setImageResource(R.drawable.ic_check);
                        })
        );
    }

    private void getTVShowDeltails()
    {
        tvShowsDetailsBinding.setIsLoading(true);
        //getting the id to make a request with it to get details of it
        String movieId = String.valueOf(tvShows.getId());
        tvShowsDetailsViewModel.getTVShowsDetails(movieId).observe(this,tvShowsDetailsResponse ->
        {
            tvShowsDetailsBinding.setIsLoading(false);
            if(tvShowsDetailsResponse.getTvShowsDetails() != null)
            {
                if(tvShowsDetailsResponse.getTvShowsDetails().getPictures() != null)
                {

                    // loading images of tvshow to the fun. "loadImageSlider" to set it to adapter of viewPager
                    loadImageSlider(tvShowsDetailsResponse.getTvShowsDetails().getPictures());

                }
            }

            // getting image of tvShow to detail activity
            tvShowsDetailsBinding.setTvShowImageURL(
                    tvShowsDetailsResponse.getTvShowsDetails().getImage_thumbnail_path()
            );
            tvShowsDetailsBinding.imageTvShow.setVisibility(View.VISIBLE);

            // set descrription of tvshow
            tvShowsDetailsBinding.setDescription(tvShowsDetailsResponse.getTvShowsDetails().getDescription());

            // handling read more action
            tvShowsDetailsBinding.readMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(tvShowsDetailsBinding.readMore.getText().equals(getString(R.string.read_more)))
                    {
                        tvShowsDetailsBinding.textDescription.setMaxLines(Integer.MAX_VALUE);
                        tvShowsDetailsBinding.textDescription.setEllipsize(null);
                        tvShowsDetailsBinding.readMore.setText(R.string.read_less);
                    }
                    else
                    {
                        tvShowsDetailsBinding.textDescription.setMaxLines(4);
                        tvShowsDetailsBinding.textDescription.setEllipsize(TextUtils.TruncateAt.END);
                        tvShowsDetailsBinding.readMore.setText(R.string.read_more);
                    }
                }
            });
            tvShowsDetailsBinding.textDescription.setVisibility(View.VISIBLE);
            tvShowsDetailsBinding.readMore.setVisibility(View.VISIBLE);



            // load rating
            tvShowsDetailsBinding.setRating(tvShowsDetailsResponse.getTvShowsDetails().getRating());



            //load geners
            if(tvShowsDetailsResponse.getTvShowsDetails().getGenres() != null)
            {
                tvShowsDetailsBinding.setGener(tvShowsDetailsResponse.getTvShowsDetails().getGenres().get(0));
            }
            else
            {
                tvShowsDetailsBinding.setGener("N/A");
            }

            // load runtime
            tvShowsDetailsBinding.setRuntime(tvShowsDetailsResponse.getTvShowsDetails().getRuntime()+"Min");
            // set Visibility to visible
            tvShowsDetailsBinding.viewDivider1.setVisibility(View.VISIBLE);
            tvShowsDetailsBinding.layoutMisc.setVisibility(View.VISIBLE);
            tvShowsDetailsBinding.viewDivider2.setVisibility(View.VISIBLE);



            // Handle button of website and episodes
            tvShowsDetailsBinding.buttonWebsite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(tvShowsDetailsResponse.getTvShowsDetails().getUrl()));
                    startActivity(i);
                }
            });
            tvShowsDetailsBinding.buttonWebsite.setVisibility(View.VISIBLE);
            tvShowsDetailsBinding.buttonEpisodes.setVisibility(View.VISIBLE);


            // handling button of episodes by creating bottom sheet
            tvShowsDetailsBinding.buttonEpisodes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(bottomSheetDialog == null)
                    {
                        bottomSheetDialog = new BottomSheetDialog(TVShowsDetailsActivity.this);
                        layoutButtomSheetBinding = DataBindingUtil.inflate(LayoutInflater.from(TVShowsDetailsActivity.this),
                                R.layout.layout_buttom_sheet, findViewById(R.id.episodeContainer),
                                false);

                        bottomSheetDialog.setContentView(layoutButtomSheetBinding.getRoot());

                        //set Adapter of EpisodesAdapter
                        layoutButtomSheetBinding.episodesRecyclerView.setAdapter(new EpisodesAdapter(tvShowsDetailsResponse.getTvShowsDetails()
                                .getEpisodes()));
                        // set title of the layout header
                        layoutButtomSheetBinding.textTitle.setText("Episodes of "+tvShows.getName());

                        // handle close button
                        layoutButtomSheetBinding.imageClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                bottomSheetDialog.dismiss();
                            }
                        });
                    }

                    // -- Section Start --
                    FrameLayout frameLayout = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);

                    if(frameLayout != null)
                    {
                        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(frameLayout);
                        bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                    // -- Section end --
                    bottomSheetDialog.show();
                }
            });



            // handling watchlist button
            tvShowsDetailsBinding.imagewatchlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isTVShowAvilableInWatchList) {
                        compositeDisposable.add(
                                tvShowsDetailsViewModel.removeTVShowFromWatchList(tvShows)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(() -> {
                                            isTVShowAvilableInWatchList = false;
                                            TempDataHolder.IS_WATCHLIST_UPDATED = true;
                                            tvShowsDetailsBinding.imagewatchlist.setImageResource(R.drawable.ic_eye);
                                            Toast.makeText(getBaseContext(), "Deleted From WatchList", Toast.LENGTH_LONG).show();
                                        })
                        );
                    } else {
                        compositeDisposable.add(
                                tvShowsDetailsViewModel.addToWatchList(tvShows)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(() -> {
                                                    TempDataHolder.IS_WATCHLIST_UPDATED = true;
                                                    tvShowsDetailsBinding.imagewatchlist.setImageResource(R.drawable.ic_check);
                                                    Toast.makeText(getBaseContext(), "Added To WatchList", Toast.LENGTH_LONG).show();
                                                }
                                        )
                        );
                    }
                }
            });
            tvShowsDetailsBinding.imagewatchlist.setVisibility(View.VISIBLE);





            // loading basic details from loadBasicTvShowDetail function.
            loadBasicTvShowDetail();
        }
        );
    }

    private void loadImageSlider(List<String> imagesSlider)
    {
        tvShowsDetailsBinding.slideViewPager2.setOffscreenPageLimit(1);
        tvShowsDetailsBinding.slideViewPager2.setAdapter(new ImageSliderAdapter(imagesSlider));
        tvShowsDetailsBinding.slideViewPager2.setVisibility(View.VISIBLE);
        tvShowsDetailsBinding.viewFadingEdge.setVisibility(View.VISIBLE);
        setupSliderIndicator(imagesSlider.size());
        tvShowsDetailsBinding.slideViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentSliderIndicator(position);
            }
        });
    }


    // functions that setup the indicators for slider...

    private void setupSliderIndicator(int count)
    {
        ImageView [] indicators = new ImageView[count];
        /*
         LayoutParams are used by views to tell their parents how they want to be laid out.
         LayoutParams class just describes how big the view wants to be for both width and height. For each dimension
        */
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                (10,10);
        layoutParams.rightMargin = 5;

        for (int i = 0; i < indicators.length ; i++) {
            indicators[i] = new ImageView(this);
            indicators[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.background_slider_indicator_2));
            indicators[i].setLayoutParams(layoutParams);
            tvShowsDetailsBinding.layoutSlideIndicator.addView(indicators[i]);
        }

            tvShowsDetailsBinding.layoutSlideIndicator.setVisibility(View.VISIBLE);
            setCurrentSliderIndicator(0);
    }

    private void setCurrentSliderIndicator(int position)
    {   // get how much indicators layoutSlideIndicator has.
        int childCount = tvShowsDetailsBinding.layoutSlideIndicator.getChildCount();
        for (int i = 0; i < childCount ; i++)
        {
            ImageView imageView = (ImageView) tvShowsDetailsBinding.layoutSlideIndicator.getChildAt(i);
            if(position == i)
            {
                imageView.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.background_slider_indicator));
            }
            else
            {
                imageView.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.background_slider_indicator_2));
            }
        }
    }

    private void loadBasicTvShowDetail()
    {
        tvShowsDetailsBinding.setTvShowName(tvShows.getName());
        tvShowsDetailsBinding.setStatus(tvShows.getStatus());
        tvShowsDetailsBinding.setNetworkCountry(tvShows.getCountry());
        tvShowsDetailsBinding.setStartedDate(tvShows.getStart_date());
        tvShowsDetailsBinding.tvShowName.setVisibility(View.VISIBLE);
        tvShowsDetailsBinding.textStatus.setVisibility(View.VISIBLE);
        tvShowsDetailsBinding.textNetworkCountry.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}