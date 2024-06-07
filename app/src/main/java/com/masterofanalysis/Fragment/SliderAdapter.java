package com.masterofanalysis.Fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.masterofanalysis.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {

    private List<String> imageUrls;
    private Context context;

    public SliderAdapter(Context context, List<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls != null ? imageUrls : new ArrayList<>();
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        String imageUrl = imageUrls.get(position);
        Glide.with(context).load(imageUrl).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    public static class SliderViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }
}
