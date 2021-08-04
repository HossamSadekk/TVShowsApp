package com.example.popularmovieapp.adapters;

import android.provider.DocumentsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovieapp.R;
import com.example.popularmovieapp.databinding.ItemContainerBinding;
import com.example.popularmovieapp.databinding.ItemContainerOfButtomSheetBinding;
import com.example.popularmovieapp.models.Episode;

import java.util.List;

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.EpisodesViewHolder>  {

    private List<Episode> episodeList;
    private LayoutInflater layoutInflater;

    public EpisodesAdapter(List<Episode> episodeList) {
        this.episodeList = episodeList;
    }

    @NonNull
    @Override
    public EpisodesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        return new EpisodesViewHolder(DataBindingUtil.inflate(layoutInflater, R.layout.item_container_of_buttom_sheet,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodesViewHolder holder, int position) {
        holder.bindEpisodes(episodeList.get(position));
    }

    @Override
    public int getItemCount() {
        return episodeList.size();
    }

    static class EpisodesViewHolder extends RecyclerView.ViewHolder{
        private ItemContainerOfButtomSheetBinding itemContainerOfButtomSheetBinding;
        public EpisodesViewHolder(ItemContainerOfButtomSheetBinding itemContainerOfButtomSheetBinding) {
            super(itemContainerOfButtomSheetBinding.getRoot());
            this.itemContainerOfButtomSheetBinding = itemContainerOfButtomSheetBinding;
        }

        public void bindEpisodes(Episode episode)
        {
            String title = "S";
            String season =String.valueOf(episode.getSeason());
            if(season.length() == 1)
            {
                season = "0".concat(season);
            }
            String episodesNumber =String.valueOf(episode.getEpisode());
            if(episodesNumber.length() == 1)
            {
                episodesNumber = "0".concat(episodesNumber);
            }
            episodesNumber = "E".concat(episodesNumber);
            title = title.concat(season).concat(episodesNumber);
            itemContainerOfButtomSheetBinding.setTitle(title);
            itemContainerOfButtomSheetBinding.setName(episode.getNameEpisode());
            itemContainerOfButtomSheetBinding.setAirDate(episode.getAir_date());


        }
    }
}
