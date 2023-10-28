package com.example.firebasesetup;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class SliderAdapterMainActivityViewPager extends RecyclerView.Adapter<SliderAdapterMainActivityViewPager.SliderViewHolder> {
    //VIEWPAGER ADAPTER
    public SliderAdapterMainActivityViewPager(List<ImageSliderModelClassFromMainActivity> sliderModelClassFromMainActivities, ViewPager2 viewpager2) {
        this.sliderModelClassFromMainActivities = sliderModelClassFromMainActivities;
        this.viewpager2 = viewpager2;
    }

    List<ImageSliderModelClassFromMainActivity> sliderModelClassFromMainActivities;
    ViewPager2 viewpager2;

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_activity_slider_items_container, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.setRoundedImageView(sliderModelClassFromMainActivities.get(position));
        if (position == sliderModelClassFromMainActivities.size() - 2) {
            viewpager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return sliderModelClassFromMainActivities.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView roundedImageView;

        SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            roundedImageView = itemView.findViewById(R.id.imageSlideRoundedImage);
        }

        void setRoundedImageView(ImageSliderModelClassFromMainActivity modelClassFromMainActivity) {
            //If you want to display image from the internet,
            //You can put code here using glide or picasso
            roundedImageView.setImageResource(modelClassFromMainActivity.getImage());
        }
    }

    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            sliderModelClassFromMainActivities.addAll(sliderModelClassFromMainActivities);
            notifyDataSetChanged();
        }
    };

}
