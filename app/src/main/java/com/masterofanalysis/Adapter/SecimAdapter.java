package com.masterofanalysis.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.masterofanalysis.DataModels.DataSecimler;
import com.masterofanalysis.R;

import java.util.List;
import java.util.Locale;

public class SecimAdapter extends RecyclerView.Adapter<SecimAdapter.ProductViewHolder> {
    Context mCtx;
    List<DataSecimler.icerik> productList;
    private OnItemClickListener mListener;
    public SecimAdapter(Context mCtx, List<DataSecimler.icerik> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    // Dinleyiciyi ayarlamak için bir metod
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mCtx).inflate(R.layout.custom_secimler, parent, false);
        ProductViewHolder productViewHolder = new ProductViewHolder(view);
        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {

        if (Locale.getDefault().getLanguage().equalsIgnoreCase("tr")) {
            holder.baslik.setText(productList.get(position).getAciklama());
        } else {
            holder.baslik.setText(productList.get(position).getAciklamaing());
        }

        if (productList.get(position).getDurum().equalsIgnoreCase("Kazandı")){
            holder.durum.setText(mCtx.getText(R.string.Won));
            holder.status.setBackgroundResource(R.drawable.kazandi);
        }else if(productList.get(position).getDurum().equalsIgnoreCase("Kaybetti")){
            holder.durum.setText(mCtx.getText(R.string.Lost));
            holder.status.setBackgroundResource(R.drawable.kaybetti);
        }else{
            holder.durum.setText(mCtx.getText(R.string.Waiting));
            holder.status.setBackgroundResource(R.drawable.bekliyor);
        }

        holder.oran.setText(productList.get(position).getToplam_oran()+" "+mCtx.getText(R.string.Odds));
        holder.kredi.setText(productList.get(position).getKredi()+" "+mCtx.getText(R.string.Credit));
        holder.satinalan.setText(productList.get(position).getOynayan_sayisi());
        holder.tarihsaat.setText(productList.get(position).getTarih()+"  "+productList.get(position).getSaat());

    }
    @Override
    public int getItemCount() {
        return productList.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView baslik,satinalan,oran,kredi,tarihsaat,durum;
        ImageView status;
        public ProductViewHolder(View itemView) {
            super(itemView);
            baslik = itemView.findViewById(R.id.baslik);
            satinalan = itemView.findViewById(R.id.satinalan);
            oran = itemView.findViewById(R.id.oran);
            kredi = itemView.findViewById(R.id.kredi);
            tarihsaat = itemView.findViewById(R.id.tarihsaat);
            durum = itemView.findViewById(R.id.durum);
            status = itemView.findViewById(R.id.status);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}