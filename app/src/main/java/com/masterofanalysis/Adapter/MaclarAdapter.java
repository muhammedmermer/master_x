package com.masterofanalysis.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.masterofanalysis.DataModels.Datamatches;
import com.masterofanalysis.R;

import java.util.List;
import java.util.Locale;

public class MaclarAdapter extends RecyclerView.Adapter<MaclarAdapter.ProductViewHolder> {
    private Context mCtx;
    private List<Datamatches.Matches> productList;

    public MaclarAdapter(Context mCtx, List<Datamatches.Matches> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.custom_maclar, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Datamatches.Matches currentMatch = productList.get(position);

        holder.home.setText(currentMatch.getEvsahibi());
        holder.away.setText(currentMatch.getDeplasman());
        holder.time.setText(currentMatch.getTarih() + " " + currentMatch.getSaat());

        if (Locale.getDefault().getLanguage().equalsIgnoreCase("tr")) {
            holder.predic.setText(currentMatch.getTahmin());
        } else {
            holder.predic.setText(currentMatch.getTahmining());
        }

        if (productList.get(position).getDurum().equalsIgnoreCase("Kazandı")){
            holder.status.setBackgroundResource(R.drawable.kazandi);
            holder.statustext.setText(mCtx.getString(R.string.Won));
        }else if(productList.get(position).getDurum().equalsIgnoreCase("Kaybetti")){
            holder.status.setBackgroundResource(R.drawable.kaybetti);
            holder.statustext.setText(mCtx.getString(R.string.Lost));
        } else {
            holder.status.setBackgroundResource(R.drawable.bekliyor);
            holder.statustext.setText(mCtx.getString(R.string.Waiting));
        }


        if (currentMatch != null) {
            String leagueName = currentMatch.getLeague_name();
            if (leagueName != null && !leagueName.isEmpty()) {
                holder.league_name.setText(leagueName);
            } else {
                holder.league_name.setText("League Name");
            }
        } else {
            holder.league_name.setText("League Name");
        }





        holder.oran.setText(currentMatch.getOran());

        if (currentMatch.getMacsonucu() != null && currentMatch.getMacsonucu().trim().length() > 1) {
            holder.result.setText(currentMatch.getMacsonucu());
        } else {
            holder.result.setText("-");
        }

        loadTeamLogo(holder.home_logo, currentMatch.getHome_logo());
        loadTeamLogo(holder.away_logo, currentMatch.getAwayLogoUrl());
    }

    private void loadTeamLogo(ImageView imageView, String logoUrl) {
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.logo);
        Glide.with(mCtx)
                .load(logoUrl)
                .apply(requestOptions)
                .into(imageView);
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView home, result, away, predic, time, oran, statustext, league_name;
        ImageView status, home_logo, away_logo;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            home = itemView.findViewById(R.id.home);
            result = itemView.findViewById(R.id.result);
            away = itemView.findViewById(R.id.away);
            predic = itemView.findViewById(R.id.predic);
            time = itemView.findViewById(R.id.time);
            oran = itemView.findViewById(R.id.oran);
            status = itemView.findViewById(R.id.status);
            statustext = itemView.findViewById(R.id.statustext);
            home_logo = itemView.findViewById(R.id.home_logo);
            away_logo = itemView.findViewById(R.id.away_logo);
            league_name = itemView.findViewById(R.id.league_name);
        }

    }
}
