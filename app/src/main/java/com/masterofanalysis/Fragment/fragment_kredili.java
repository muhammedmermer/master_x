package com.masterofanalysis.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.masterofanalysis.Activity.MainActivity;
import com.masterofanalysis.Adapter.MaclarAdapter;
import com.masterofanalysis.Adapter.SecimAdapter;
import com.masterofanalysis.Constants;
import com.masterofanalysis.DataModels.DataSecimler;
import com.masterofanalysis.DataModels.Datamatches;
import com.masterofanalysis.Function;
import com.masterofanalysis.R;
import com.masterofanalysis.api.ApiClient;
import com.masterofanalysis.api.ApiService;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_kredili extends Fragment {
    RecyclerView rvm;
    SecimAdapter adapter;
    private List<DataSecimler.icerik> matchesList;
    private List<Datamatches.Matches> matchesListic;
    MaclarAdapter adapteric;
    View view;
    private FirebaseAuth auth;
    ApiService apiService;
    private Context mContext;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }
    public void checkIfFragmentAttached(ContextOperation operation) {
        if (isAdded() && getContext() != null) {
            operation.performOperation(requireContext());
        }
    }
    public interface ContextOperation {
        void performOperation(Context context);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment__kredili, container, false);
        if (mContext!=null) {
            Function.showLoading(getActivity(), mContext);
            apiService = ApiClient.getClient().create(ApiService.class);
            auth = FirebaseAuth.getInstance();
            rvm = view.findViewById(R.id.rvm);
            rvm.setHasFixedSize(true);
            rvm.setLayoutManager(new LinearLayoutManager(mContext));
            matchesList = new ArrayList<>();
            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            Call<DataSecimler> call = apiService.GetSecimler("kuponlar", auth.getUid(), Function.getTimezone(), Constants.API_KEY);
            call.enqueue(new Callback<DataSecimler>() {
                @Override
                public void onResponse(Call<DataSecimler> call, Response<DataSecimler> response) {
                    try {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus().equalsIgnoreCase("success")) {
                                DataSecimler model = response.body();
                                matchesList = model.getData();
                                setadapter();
                                Function.dismissLoading(getActivity());
                            } else {
                                Function.showToast(mContext, mContext.getString(R.string.kayit_hatasi), 1);
                            }
                        } else {
                            Function.showToast(mContext, mContext.getString(R.string.kayit_hatasi), 1);
                        }
                    } catch (Exception e) {
                        Function.showToast(mContext, mContext.getString(R.string.kayit_hatasi), 1);
                    }
                }

                @Override
                public void onFailure(Call<DataSecimler> call, Throwable t) {
                    Function.showToast(mContext, mContext.getString(R.string.kayit_hatasi), 1);
                }
            });
        }
        return view;
    }

    private void setadapter() {
        adapter = new SecimAdapter(mContext, matchesList);
        rvm.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(position -> {
            Function.showLoading(getActivity(),mContext);

            if (matchesList.get(position).getDurum().equalsIgnoreCase("Bekliyor")){
                Call<Datamatches> call = apiService.GetSecimleric(
                        "kuponkontrol",
                        auth.getUid(),
                        Function.getTimezone(),
                        matchesList.get(position).getKupon_id(),
                        Constants.API_KEY);
                call.enqueue(new Callback<Datamatches>() {
                    @Override
                    public void onResponse(Call<Datamatches> call, Response<Datamatches> response) {
                        try {
                            if (response.isSuccessful()) {
                                if (response.body().getStatus().equalsIgnoreCase("success")) {
                                    icgetir(position);
                                } else {
                                    checkIfFragmentAttached(new ContextOperation() {
                                        @Override
                                        public void performOperation(Context context) {
                                            Function.dismissLoading(getActivity());
                                            final Dialog dialogonay = new Dialog(context, R.style.Theme_BottomNavigation);
                                            dialogonay.setContentView(R.layout.custom_onay);
                                            Objects.requireNonNull(dialogonay.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                            TextView secim_baslik = dialogonay.findViewById(R.id.secim_baslik);
                                            TextView secim_aciklama = dialogonay.findViewById(R.id.secim_aciklama);
                                            TextView cancel = dialogonay.findViewById(R.id.cancel);
                                            TextView okey = dialogonay.findViewById(R.id.okey);
                                            secim_baslik.setText(context.getString(R.string.CouponWillBeOpened));
                                            secim_aciklama.setText(context.getString(R.string.Doyouconfirm));
                                            cancel.setOnClickListener(view1 -> dialogonay.dismiss());
                                            okey.setOnClickListener(view1 -> {
                                                dialogonay.dismiss();
                                                icgetir(position);
                                            });
                                            dialogonay.show();
                                        }
                                    });
                                }
                            } else {
                                Function.dismissLoading(getActivity());
                                Function.showToast(mContext, mContext.getString(R.string.kayit_hatasi), 1);
                            }
                        } catch (Exception e) {
                            Function.dismissLoading(getActivity());
                            Function.showToast(mContext, mContext.getString(R.string.kayit_hatasi), 1);
                        }
                    }
                    @Override
                    public void onFailure(Call<Datamatches> call, Throwable t) {
                        Function.dismissLoading(getActivity());
                        Function.showToast(mContext, mContext.getString(R.string.kayit_hatasi), 1);
                    }
                });
            }else{
                icgetir(position);
            }
        });
    }
    private void icgetir(int position) {
        checkIfFragmentAttached(new ContextOperation() {
            @Override
            public void performOperation(Context context) {
                final Dialog dialog = new Dialog(context, R.style.Theme_BottomNavigation);
                dialog.setContentView(R.layout.custom_secimler_ic);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                RecyclerView rvmic = dialog.findViewById(R.id.rvm);
                Button btnic = dialog.findViewById(R.id.itsokey);
                TextView baslik = dialog.findViewById(R.id.baslik);
                TextView satinalan = dialog.findViewById(R.id.satinalan);
                TextView oran = dialog.findViewById(R.id.oran);
                TextView kredi = dialog.findViewById(R.id.kredi);
                rvmic.setHasFixedSize(true);
                rvmic.setLayoutManager(new LinearLayoutManager(context));
                matchesListic = new ArrayList<>();
                Call<Datamatches> call = apiService.GetSecimleric(
                        "kuponlaric",
                        auth.getUid(),
                        Function.getTimezone(),
                        matchesList.get(position).getKupon_id(),
                        Constants.API_KEY);
                call.enqueue(new Callback<Datamatches>() {
                    @Override
                    public void onResponse(Call<Datamatches> call, Response<Datamatches> response) {
                        try {
                            if (response.isSuccessful()) {
                                if (response.body().getStatus().equalsIgnoreCase("success")) {
                                    dialog.show();

                                    if (Locale.getDefault().getLanguage().equalsIgnoreCase("tr")) {
                                        baslik.setText(matchesList.get(position).getAciklama());
                                    } else {
                                        baslik.setText(matchesList.get(position).getAciklamaing());
                                    }
                                    satinalan.setText(matchesList.get(position).getOynayan_sayisi());
                                    oran.setText(matchesList.get(position).getToplam_oran());
                                    kredi.setText(matchesList.get(position).getKredi());

                                    Datamatches model = response.body();
                                    matchesListic = model.getData();
                                    adapteric = new MaclarAdapter(context, matchesListic);
                                    rvmic.setAdapter(adapteric);
                                    adapteric.notifyDataSetChanged();
                                    Function.dismissLoading(getActivity());
                                } else {
                                    if (response.body().getStatus().equalsIgnoreCase("yetersiz_kredi")) {
                                        checkIfFragmentAttached(new ContextOperation() {
                                            @Override
                                            public void performOperation(Context context) {
                                                Function.dismissLoading(getActivity());
                                                final Dialog dialog = new Dialog(context, R.style.Theme_BottomNavigation);
                                                dialog.setContentView(R.layout.custom_dialog);
                                                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                                Button btn = dialog.findViewById(R.id.itsokey);
                                                TextView referencebaslik = dialog.findViewById(R.id.referencebaslik);
                                                TextView referenceaciklama = dialog.findViewById(R.id.referenceaciklama);
                                                LottieAnimationView anim = dialog.findViewById(R.id.profildetay);
                                                anim.setAnimation("noseckin.json");
                                                referencebaslik.setText(context.getString(R.string.InsufficientBalance));
                                                referenceaciklama.setText(context.getString(R.string.gotopaymentpage));
                                                btn.setOnClickListener(view2 -> {
                                                    dialog.dismiss();
                                                    if (getActivity() != null && getActivity() instanceof MainActivity) {
                                                        ((MainActivity) getActivity()).replaceFragmentx(new fragment_odeme(),"odeme");
                                                    }
                                                });
                                                dialog.show();
                                            }
                                        });
                                    } else if (response.body().getStatus().equalsIgnoreCase("update_error")) {
                                        Function.showToast(context, "Update error", 1);
                                        Function.dismissLoading(getActivity());
                                    } else {
                                        Function.dismissLoading(getActivity());
                                        Function.showToast(context, context.getString(R.string.kayit_hatasi), 1);
                                    }
                                }
                            } else {
                                Function.dismissLoading(getActivity());
                                Function.showToast(context, context.getString(R.string.kayit_hatasi), 1);
                            }
                        } catch (Exception e) {
                            Function.dismissLoading(getActivity());
                            Function.showToast(context, context.getString(R.string.kayit_hatasi), 1);
                        }
                    }
                    @Override
                    public void onFailure(Call<Datamatches> call, Throwable t) {
                        Function.dismissLoading(getActivity());
                        Function.showToast(context, context.getString(R.string.kayit_hatasi), 1);
                    }
                });
                btnic.setOnClickListener(view2 -> {
                    dialog.dismiss();
                });
            }
        });
    }
}