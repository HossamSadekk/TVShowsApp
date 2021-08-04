package com.example.popularmovieapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovieapp.R;
import com.example.popularmovieapp.databinding.ActivityTVShowsDetailsBinding;
import com.example.popularmovieapp.databinding.ItemContainerBinding;
import com.example.popularmovieapp.databinding.ItemSliderBinding;

import java.util.ArrayList;
import java.util.List;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder> {
private List<String> sliderImage;
LayoutInflater layoutInflater;

    public ImageSliderAdapter(List<String> sliderImage) {
        this.sliderImage = sliderImage;
    }

    @NonNull
    @Override
    public ImageSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        ItemSliderBinding itemContainerBinding = DataBindingUtil.inflate(layoutInflater,R.layout.item_slider,parent,false);
        return new ImageSliderViewHolder(itemContainerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSliderViewHolder holder, int position) {
        holder.BindSlidingImage(sliderImage.get(position));
    }

    @Override
    public int getItemCount() {
        return sliderImage.size();
    }

    static class ImageSliderViewHolder extends RecyclerView.ViewHolder
    {
        private ItemSliderBinding itemSliderBinding;

        public ImageSliderViewHolder(ItemSliderBinding itemSliderBinding) {
            super(itemSliderBinding.getRoot());
            this.itemSliderBinding = itemSliderBinding;
        }

        public void BindSlidingImage(String ImageURL)
        {
            itemSliderBinding.setImageURL(ImageURL);
        }
    }
}
