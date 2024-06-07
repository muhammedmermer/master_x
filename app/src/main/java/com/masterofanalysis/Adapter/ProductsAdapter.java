package com.masterofanalysis.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.masterofanalysis.DataModels.ProductsModel;
import com.masterofanalysis.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {
    Context mCtx;
    List<ProductsModel.Products> productList;
    private OnItemClickListener mListener;
    private CountDownTimer countDownTimer;
    public ProductsAdapter(Context mCtx, List<ProductsModel.Products> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }
    public void setProductsList(List<ProductsModel.Products> productList){
        this.productList = productList;
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mCtx).inflate(R.layout.custom_products, parent, false);
        ProductViewHolder productViewHolder = new ProductViewHolder(view);
        return productViewHolder;
    }
    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        //Function.showToast(getContext(),productsList.get(position).getNow_time(),1);
        if (productList.get(position).getProductType().equalsIgnoreCase("SUBS")){
            holder.info.setVisibility(View.VISIBLE);
        }
        if (Locale.getDefault().getLanguage().equalsIgnoreCase("tr")) {
            holder.baslik.setText(productList.get(position).getProduct_title_tr());
            holder.aciklama.setText(productList.get(position).getProduct_body_tr());
            if (productList.get(position).getAciklama_tr() != null){
                if (productList.get(position).getAciklama_tr().trim().length()>0){
                    holder.lytkampanya.setVisibility(View.VISIBLE);
                    holder.kampanya.setText(productList.get(position).getAciklama_tr());

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    long differenceInMillis = 0;
                    try {
                        Date date1 = sdf.parse(productList.get(position).getKampanya_bitis_tarihi());
                        Date date2 = sdf.parse(productList.get(position).getNow_time());
                        if (date1 != null && date2 != null) {
                            differenceInMillis = Math.abs(date1.getTime() - date2.getTime());
                        }
                        countDownTimer = new CountDownTimer(differenceInMillis, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                long seconds = millisUntilFinished / 1000;
                                String timeLeft = mCtx.getString(R.string.kampanyayakalansure)+" : "+formatSeconds(seconds);
                                holder.kampanyabitis.setText(timeLeft);
                            }
                            @Override
                            public void onFinish() {
                                holder.kampanyabitis.setText(mCtx.getString(R.string.suredoldu));
                            }
                        }.start();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }else{
                    holder.lytkampanya.setVisibility(View.INVISIBLE);
                }
            }else{
                holder.lytkampanya.setVisibility(View.INVISIBLE);
            }
        } else {
            holder.baslik.setText(productList.get(position).getProduct_title_en());
            holder.aciklama.setText(productList.get(position).getProduct_body_en());
            if (productList.get(position).getAciklama_en() != null){
                if (productList.get(position).getAciklama_en().trim().length()>0){
                    holder.lytkampanya.setVisibility(View.VISIBLE);
                    holder.kampanya.setText(productList.get(position).getAciklama_en());

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    long differenceInMillis = 0;
                    try {
                        Date date1 = sdf.parse(productList.get(position).getKampanya_bitis_tarihi());
                        Date date2 = sdf.parse(productList.get(position).getNow_time());
                        if (date1 != null && date2 != null) {
                            differenceInMillis = Math.abs(date1.getTime() - date2.getTime());
                        }
                        countDownTimer = new CountDownTimer(differenceInMillis, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                long seconds = millisUntilFinished / 1000;
                                String timeLeft = mCtx.getString(R.string.kampanyayakalansure)+" : "+formatSeconds(seconds);
                                holder.kampanyabitis.setText(timeLeft);
                            }
                            @Override
                            public void onFinish() {
                                holder.kampanyabitis.setText(mCtx.getString(R.string.suredoldu));
                            }
                        }.start();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }else{
                    holder.lytkampanya.setVisibility(View.INVISIBLE);
                }
            }else{
                holder.lytkampanya.setVisibility(View.INVISIBLE);
            }
        }
        if (productList.get(position).getProductPrize() != null){
            if (productList.get(position).getProductPrize().trim().length()>0){
                holder.price.setText(productList.get(position).getProductPrize());
            }
        }
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
        TextView baslik,price,aciklama,kampanya,kampanyabitis;
        LinearLayout lytkampanya;
        CardView satinal;
        ImageView info;
        public ProductViewHolder(View itemView) {
            super(itemView);
            baslik = itemView.findViewById(R.id.baslik);
            price = itemView.findViewById(R.id.price);
            aciklama = itemView.findViewById(R.id.aciklama);
            kampanya = itemView.findViewById(R.id.kampanya);
            kampanyabitis = itemView.findViewById(R.id.kampanyabitis);
            lytkampanya = itemView.findViewById(R.id.lytkampanya);
            satinal = itemView.findViewById(R.id.satinal);
            info = itemView.findViewById(R.id.info);
            info.setOnClickListener(view -> {
                final Dialog dialog = new Dialog(mCtx, R.style.Theme_BottomNavigation);
                dialog.setContentView(R.layout.custom_dialog);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Button btn = dialog.findViewById(R.id.itsokey);
                TextView referencebaslik = dialog.findViewById(R.id.referencebaslik);
                TextView referenceaciklama = dialog.findViewById(R.id.referenceaciklama);
                LottieAnimationView anim = dialog.findViewById(R.id.profildetay);
                anim.setVisibility(View.GONE);
                referencebaslik.setText(mCtx.getString(R.string.abonelik_hakkinda_baslik));
                if (Locale.getDefault().getLanguage().equalsIgnoreCase("tr")) {
                    referenceaciklama.setText("Aboneliğiniz,\n" +
                            "ürün açıklamasında belirtilen her fatura döneminde otomatik olarak yenilenir.\n" +
                            "Geçerli bir aboneliği istediğiniz zaman durdurabilirsiniz.\n" +
                            "Android cihazınızda Google Play'deki aboneliklere gidin.\n" +
                            "İptal etmek istediğiniz aboneliği seçin.\n" +
                            "Aboneliği iptal et'e dokunun.\n" +
                            "Talimatları uygulayın.");
                }else{
                    referenceaciklama.setText("Your subscription,\n" +
                            "automatically renews at each billing cycle as stated in the product description.\n" +
                            "You can stop a valid subscription at any time.\n" +
                            "Go to subscriptions in Google Play on your Android device.\n" +
                            "Select the subscription you want to cancel.\n" +
                            "Tap on Cancel Subscription.\n" +
                            "Follow the instructions.");
                }
                btn.setOnClickListener(view2 -> {
                    dialog.dismiss();
                });
                dialog.show();
            });
            satinal.setOnClickListener(view -> {
                if (mListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.onItemClick(position);
                    }
                }
            });
        }
    }
    private String formatSeconds(long seconds) {
        long days = seconds / (24 * 3600);
        seconds = seconds % (24 * 3600);
        long hours = seconds / 3600;
        seconds %= 3600;
        long minutes = seconds / 60;
        seconds %= 60;

        if (days > 0) {
            return  String.format(mCtx.getString(R.string.day), days) + " " +
                    String.format(mCtx.getString(R.string.hours), hours) + " " +
                    String.format(mCtx.getString(R.string.minute), minutes) + " " +
                    String.format(mCtx.getString(R.string.second), seconds);
        } else if (hours > 0) {
            return String.format(mCtx.getString(R.string.hours), hours) + " " +
                    String.format(mCtx.getString(R.string.minute), minutes) + " " +
                    String.format(mCtx.getString(R.string.second), seconds);
        } else {
            return String.format(mCtx.getString(R.string.minute), minutes) + " " +
                    String.format(mCtx.getString(R.string.second), seconds);
        }
        /*
        if (days > 0) {
            return String.format("%d Gün %d Saat %d Dakika %d Saniye", days, hours, minutes, seconds);
        } else if (hours > 0) {
            return String.format("%d Saat %d Dakika %d Saniye", hours, minutes, seconds);
        } else {
            return String.format("%d Dakika %d Saniye", minutes, seconds);
        }

         */
    }
}