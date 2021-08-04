package com.example.popularmovieapp.adapters;

import android.view.LayoutInflater;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovieapp.R;
import com.example.popularmovieapp.databinding.ItemContainerBinding;
import com.example.popularmovieapp.listeners.TVShowsListener;
import com.example.popularmovieapp.models.TVShows;

import java.util.List;

public class TVShowAdapter extends RecyclerView.Adapter<TVShowAdapter.TVShowViewHolder> {

    private List<TVShows> tvShowsList;
    private TVShowsListener tvShowsListener;

    public TVShowAdapter(List<TVShows> tvShowsList, TVShowsListener tvShowsListener) {
        this.tvShowsList = tvShowsList;
        this.tvShowsListener = tvShowsListener;
    }

    @NonNull
    @Override
    public TVShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemContainerBinding itemContainerBinding = DataBindingUtil.inflate(inflater, R.layout.item_container,parent,false);
        return new TVShowViewHolder(itemContainerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TVShowViewHolder holder, int position) {
    holder.bind(tvShowsList.get(position));
    }

    @Override
    public int getItemCount() {
        return tvShowsList.size();
    }

    class TVShowViewHolder extends RecyclerView.ViewHolder
    {
        private ItemContainerBinding binding;
        public TVShowViewHolder(@NonNull ItemContainerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(TVShows tvShows) {
            binding.setTvShow(tvShows); // {{ In order to pass the data to the XML }}
            binding.executePendingBindings(); // {{  is important in order to execute the data binding immediately }}
            binding.getRoot().setOnClickListener(view -> tvShowsListener.onTVShowClicked(tvShows));//It is used to access the root of the view hierarchy
        }
    }
}
