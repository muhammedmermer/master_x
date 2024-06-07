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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.masterofanalysis.Activity.MainActivity;
import com.masterofanalysis.Adapter.MaclarAdapter;
import com.masterofanalysis.Constants;
import com.masterofanalysis.DataModels.Datamatches;
import com.masterofanalysis.Function;
import com.masterofanalysis.R;
import com.masterofanalysis.api.ApiClient;
import com.masterofanalysis.api.ApiService;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_seckin extends Fragment {
    RecyclerView rvm;
    MaclarAdapter adapter;
    CardView crdnovip;
    private List<Datamatches.Matches> matchesList;
    View view;
    private FirebaseAuth auth;
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
    public void checkIfFragmentAttached(fragment_kredili.ContextOperation operation) {
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
        view = inflater.inflate(R.layout.fragment__seckin , container , false);
        if (mContext != null) {
            Function.showLoading(getActivity(), mContext);
            auth = FirebaseAuth.getInstance();
            rvm = view.findViewById(R.id.rvm);
            crdnovip = view.findViewById(R.id.crdnovip);
            rvm.setHasFixedSize(true);
            rvm.setLayoutManager(new LinearLayoutManager(mContext));
            matchesList = new ArrayList<>();

            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            Call<Datamatches> call = apiService.GetSeckin("vip_maclar", auth.getUid(), Function.getTimezone(), Constants.API_KEY);
            call.enqueue(new Callback<Datamatches>() {
                @Override
                public void onResponse(Call<Datamatches> call, Response<Datamatches> response) {
                    try {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus().equalsIgnoreCase("success")) {
                                Datamatches model = response.body();
                                matchesList = model.getData();
                                setadapter();
                                Function.dismissLoading(getActivity());
                                crdnovip.setVisibility(View.GONE);
                            } else if (response.body().getStatus().equalsIgnoreCase("succesnos")) {
                                Datamatches model = response.body();
                                matchesList = model.getData();
                                setadapter();
                                Function.dismissLoading(getActivity());
                                crdnovip.setVisibility(View.VISIBLE);
                                Button btn = view.findViewById(R.id.itsokey);
                                TextView referencebaslik = view.findViewById(R.id.referencebaslik);
                                TextView referenceaciklama = view.findViewById(R.id.referenceaciklama);
                                LottieAnimationView anim = view.findViewById(R.id.profildetay);
                                anim.setAnimation("noseckin.json");
                                referencebaslik.setText(mContext.getString(R.string.noelitemember));
                                referenceaciklama.setText(mContext.getString(R.string.gotopaymentpageelite));
                                btn.setOnClickListener(view2 -> {
                                    if (getActivity() != null && getActivity() instanceof MainActivity) {
                                        ((MainActivity) getActivity()).replaceFragmentx(new fragment_odeme(), "odeme");
                                    }
                                });
                            } else {
                                //vip üye değil
                                checkIfFragmentAttached(new fragment_kredili.ContextOperation() {
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
                                        referencebaslik.setText(context.getString(R.string.noelitemember));
                                        referenceaciklama.setText(context.getString(R.string.gotopaymentpageelite));
                                        btn.setOnClickListener(view2 -> {
                                            if (getActivity() != null && getActivity() instanceof MainActivity) {
                                                ((MainActivity) getActivity()).replaceFragmentx(new fragment_odeme(), "odeme");
                                            }
                                        });
                                        dialog.show();
                                    }
                                });
                                //Function.showToast(mContext,mContext.getString(R.string.kayit_hatasi),1);
                            }
                        } else {
                            Function.showToast(mContext, mContext.getString(R.string.kayit_hatasi), 1);
                        }
                    } catch (Exception e) {
                        Function.showToast(mContext, mContext.getString(R.string.kayit_hatasi), 1);
                    }
                }

                @Override
                public void onFailure(Call<Datamatches> call, Throwable t) {
                    Function.showToast(mContext, mContext.getString(R.string.kayit_hatasi), 1);
                }
            });
        }
        return view;
    }
    private void setadapter(){
        adapter = new MaclarAdapter(mContext, matchesList);
        rvm.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}