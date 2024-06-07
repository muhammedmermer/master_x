package com.masterofanalysis.Fragment;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.masterofanalysis.Activity.Activity_PrivacyPolicy;
import com.masterofanalysis.Activity.activity_Splash;
import com.masterofanalysis.Constants;
import com.masterofanalysis.DataModels.GetResponseModel;
import com.masterofanalysis.DataModels.GetUserModel;
import com.masterofanalysis.DataModels.SetReferenceModel;
import com.masterofanalysis.Function;
import com.masterofanalysis.R;
import com.masterofanalysis.api.ApiClient;
import com.masterofanalysis.api.ApiService;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_Profil extends Fragment {
    LinearLayout lyt,lytyazi,lytreference;
    View view,viewust,viewalt;
    ImageView imgsoru,settings_app;
    TextView isim,email,kredi_bakiye,seckin_uyelik,referans_id;
    EditText referans_two;
    private FirebaseAuth auth;
    Button containedButton;
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
        view = inflater.inflate(R.layout.fragment__profil , container , false);
        if (mContext != null) {
            viewalt = view.findViewById(R.id.viewalt);
            lyt = view.findViewById(R.id.lyt);
            lytyazi = view.findViewById(R.id.lytyazi);
            imgsoru = view.findViewById(R.id.imgsoru);
            isim = view.findViewById(R.id.isim);
            email = view.findViewById(R.id.email);
            kredi_bakiye = view.findViewById(R.id.kredi_bakiye);
            seckin_uyelik = view.findViewById(R.id.seckin_uyelik);
            referans_id = view.findViewById(R.id.referans_id);
            referans_two = view.findViewById(R.id.referans_two);
            containedButton = view.findViewById(R.id.containedButton);
            lytreference = view.findViewById(R.id.lytreference);
            settings_app = view.findViewById(R.id.settings_app);

            auth = FirebaseAuth.getInstance();
            Function.showLoading(getActivity(), mContext);

            settings_app.setOnClickListener(view1 -> {
                checkIfFragmentAttached(new fragment_kredili.ContextOperation() {
                    @Override
                    public void performOperation(Context context) {
                        final Dialog dialogayarlar = new Dialog(context, R.style.Theme_BottomNavigation);
                        dialogayarlar.setContentView(R.layout.custom_ayarlar);
                        Objects.requireNonNull(dialogayarlar.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialogayarlar.show();
                        Button privacy_policy = dialogayarlar.findViewById(R.id.privacy_policy);
                        Button close = dialogayarlar.findViewById(R.id.close);
                        Button delete_user = dialogayarlar.findViewById(R.id.delete_user);
                        LinearLayout lyt_iletisim = dialogayarlar.findViewById(R.id.lyt_iletisim);
                        if (Constants.Instagram_Link != null && Constants.Instagram_Link.trim().length() > 1) {
                            lyt_iletisim.setVisibility(View.VISIBLE);
                        }
                        if (Constants.Whatsapp_Link != null && Constants.Whatsapp_Link.trim().length() > 1) {
                            lyt_iletisim.setVisibility(View.VISIBLE);
                        }
                        if (Constants.Telegram_Link != null && Constants.Telegram_Link.trim().length() > 1) {
                            lyt_iletisim.setVisibility(View.VISIBLE);
                        }
                        if (Constants.Youtube_Link != null && Constants.Youtube_Link.trim().length() > 1) {
                            lyt_iletisim.setVisibility(View.VISIBLE);
                        }
                        if (Constants.Website_Link != null && Constants.Website_Link.trim().length() > 1) {
                            lyt_iletisim.setVisibility(View.VISIBLE);
                        }
                        ImageView imginstagram = dialogayarlar.findViewById(R.id.imginstagram);
                        ImageView imgwhatsapp = dialogayarlar.findViewById(R.id.imgwhatsapp);
                        ImageView imgtelegram = dialogayarlar.findViewById(R.id.imgtelegram);
                        ImageView imgyoutube = dialogayarlar.findViewById(R.id.imgyoutube);
                        ImageView imgchrome = dialogayarlar.findViewById(R.id.imgchrome);
                        if (Constants.Instagram_Link != null && Constants.Instagram_Link.trim().length() > 1) {
                            imginstagram.setVisibility(View.VISIBLE);
                        }
                        if (Constants.Whatsapp_Link != null && Constants.Whatsapp_Link.trim().length() > 1) {
                            imgwhatsapp.setVisibility(View.VISIBLE);
                        }
                        if (Constants.Telegram_Link != null && Constants.Telegram_Link.trim().length() > 1) {
                            imgtelegram.setVisibility(View.VISIBLE);
                        }
                        if (Constants.Youtube_Link != null && Constants.Youtube_Link.trim().length() > 1) {
                            imgyoutube.setVisibility(View.VISIBLE);
                        }
                        if (Constants.Website_Link != null && Constants.Website_Link.trim().length() > 1) {
                            imgchrome.setVisibility(View.VISIBLE);
                        }
                        imginstagram.setOnClickListener(view -> {
                            String appUri = "https://instagram.com/_u/" + Constants.Instagram_Link;
                            Uri browserUri = Uri.parse("https://instagram.com/" + Constants.Instagram_Link);
                            try {
                                PackageManager pm = getActivity().getPackageManager();
                                pm.getPackageInfo("com.instagram.android", PackageManager.GET_ACTIVITIES);
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(appUri));
                                startActivity(i);
                            } catch (PackageManager.NameNotFoundException e) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, browserUri);
                                startActivity(browserIntent);
                            }
                        });
                        imgchrome.setOnClickListener(view -> {
                            try {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.Website_Link));
                                startActivity(browserIntent);
                            } catch (Exception e) {
                            }
                        });
                        imgtelegram.setOnClickListener(view -> {
                            try {
                                Intent telegram = new Intent(Intent.ACTION_VIEW, Uri.parse("https://telegram.me/" + Constants.Telegram_Link));
                                startActivity(telegram);
                            } catch (Exception e) {
                            }
                        });
                        imgwhatsapp.setOnClickListener(view -> {
                            String url = "https://api.whatsapp.com/send?phone=" + Constants.Whatsapp_Link;
                            try {
                                PackageManager pm = getActivity().getPackageManager();
                                pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);
                            } catch (PackageManager.NameNotFoundException e) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                            }
                        });
                        imgyoutube.setOnClickListener(view -> {
                            try {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.Youtube_Link));
                                startActivity(browserIntent);
                            } catch (Exception e) {
                            }
                        });
                        delete_user.setOnClickListener(view2 -> {
                            final Dialog dialogonay = new Dialog(context, R.style.Theme_BottomNavigation);
                            dialogonay.setContentView(R.layout.custom_delete_account);
                            Objects.requireNonNull(dialogonay.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialogonay.show();
                            EditText edittext = dialogonay.findViewById(R.id.referans_two);
                            TextView cancel = dialogonay.findViewById(R.id.cancel);
                            TextView okey = dialogonay.findViewById(R.id.okey);
                            cancel.setOnClickListener(view3 -> dialogonay.dismiss());
                            okey.setOnClickListener(view3 -> {
                                if (Locale.getDefault().getLanguage().equalsIgnoreCase("tr")) {
                                    if (edittext.getText().toString().equals("ONAYLA")) {
                                        delete_user();
                                    } else {
                                        edittext.setError("");
                                        Function.showToast(context, context.getString(R.string.error_delete_user), 1);
                                    }
                                } else {
                                    if (edittext.getText().toString().equals("CONFIRM")) {
                                        delete_user();
                                    } else {
                                        edittext.setError("");
                                        Function.showToast(context, context.getString(R.string.error_delete_user), 1);
                                    }
                                }
                            });
                        });
                        privacy_policy.setOnClickListener(view2 -> {
                            startActivity(new Intent(getActivity(), Activity_PrivacyPolicy.class).putExtra("fragment_privacy", true));
                        });
                        close.setOnClickListener(view2 -> {
                            dialogayarlar.dismiss();
                        });
                    }
                });
            });
            if (!Constants.reference_control) {
                lytreference.setVisibility(View.GONE);
            }
            containedButton.setOnClickListener(view1 -> {
                try {
                    if (referans_two.getText().toString().trim().length() == 10) {
                        if (referans_two.getText().toString().equalsIgnoreCase(referans_id.getText().toString())) {
                            referans_two.setError(mContext.getString(R.string.YouCannotWrite));
                        } else {
                            postreference(referans_two.getText().toString().trim());
                        }
                    } else {
                        referans_two.setError(mContext.getString(R.string.MustBeDigits));
                    }
                } catch (Exception e) {
                    Function.showToast(mContext, mContext.getString(R.string.kayit_hatasi), 1);
                }
            });
            imgsoru.setOnClickListener(view1 -> {
                checkIfFragmentAttached(new fragment_kredili.ContextOperation() {
                    @Override
                    public void performOperation(Context context) {
                        final Dialog dialog = new Dialog(context, R.style.Theme_BottomNavigation);
                        dialog.setContentView(R.layout.custom_dialog);
                        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        Button btn = dialog.findViewById(R.id.itsokey);
                        TextView referencebaslik = dialog.findViewById(R.id.referencebaslik);
                        TextView referenceaciklama = dialog.findViewById(R.id.referenceaciklama);
                        LottieAnimationView anim = dialog.findViewById(R.id.profildetay);
                        anim.setAnimation("suprise.json");

                        if (Locale.getDefault().getLanguage().equalsIgnoreCase("tr")) {
                            referencebaslik.setText(Constants.reference_title_tr);
                            referenceaciklama.setText(Constants.reference_exp_tr);
                        } else {
                            referencebaslik.setText(Constants.reference_title_en);
                            referenceaciklama.setText(Constants.reference_exp_en);
                        }
                        btn.setOnClickListener(view2 -> {
                            dialog.dismiss();
                        });
                        dialog.show();
                    }
                });
            });
            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            getuser_api(apiService.GetUser(
                    "get_user",
                    auth.getUid(),
                    Function.getTimezone(),
                    Constants.API_KEY));
        }
        return view;
    }
    private void delete_user(){
        Function.showLoading(getActivity(),mContext);
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<GetResponseModel> callPost = apiService.Delete_user(
                "delete_user",
                Constants.API_KEY,
                auth.getUid()
        );
        callPost.enqueue(new Callback<GetResponseModel>() {
            @Override
            public void onResponse(Call<GetResponseModel> call, Response<GetResponseModel> response) {
                try{
                    if (response.isSuccessful()) {
                        if (response.body().getResult().equalsIgnoreCase("success")){
                            auth.signOut();
                            startActivity(new Intent(mContext, activity_Splash.class));
                            getActivity().finish();
                        }else{
                            Function.showToast(mContext,mContext.getString(R.string.kayit_hatasi),1);
                        }
                    } else {
                        Function.showToast(mContext,mContext.getString(R.string.kayit_hatasi),1);
                    }
                }catch (Exception e){
                    Function.showToast(mContext,mContext.getString(R.string.kayit_hatasi),1);
                }
            }
            @Override
            public void onFailure(Call<GetResponseModel> call, Throwable t) {
                Function.showToast(mContext,mContext.getString(R.string.kayit_hatasi),1);
            }
        });
    }
    private void postreference(String referanceid){
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<SetReferenceModel> callPost = apiService.SetReferance(
                "set_referance",
                Constants.API_KEY,
                auth.getUid(),
                referanceid
        );
        callPost.enqueue(new Callback<SetReferenceModel>() {
            @Override
            public void onResponse(Call<SetReferenceModel> call, Response<SetReferenceModel> response) {
                try{
                    if (response.isSuccessful()) {
                        if (response.body().getResult().equalsIgnoreCase("success")){
                            Function.showToast(mContext,mContext.getString(R.string.YouHaveSuccessfully),1);
                            getuser_api(apiService.GetUser(
                                    "get_user",
                                    auth.getUid(),
                                    Function.getTimezone(),
                                    Constants.API_KEY));
                        }else{
                            Function.showToast(mContext,mContext.getString(R.string.no_referanceid),1);
                        }
                    } else {
                        Function.showToast(mContext,mContext.getString(R.string.no_referanceid),1);
                    }
                }catch (Exception e){
                    Function.showToast(mContext,mContext.getString(R.string.no_referanceid),1);
                }
            }
            @Override
            public void onFailure(Call<SetReferenceModel> call, Throwable t) {
                Function.showToast(mContext,mContext.getString(R.string.kayit_hatasi),1);
            }
        });
    }
    private void getuser_api(Call<GetUserModel> callPost){
        callPost.enqueue(new Callback<GetUserModel>() {
            @Override
            public void onResponse(Call<GetUserModel> call, Response<GetUserModel> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body().getResult().equalsIgnoreCase("success")) {
                            isim.setText(auth.getCurrentUser().getDisplayName());
                            email.setText(auth.getCurrentUser().getEmail());
                            kredi_bakiye.setText(response.body().getCredit_count()+"");
                            seckin_uyelik.setText(response.body().getVip_date());
                            referans_id.setText(response.body().getReferans_id());
                            if (response.body().getReferans_id_two() != null && response.body().getReferans_id_two().trim().length() > 0){
                                referans_two.setText(response.body().getReferans_id_two());
                                referans_two.setEnabled(false);
                                containedButton.setEnabled(false);
                            }
                            Function.dismissLoading(getActivity());
                        }else{
                            Function.showToast(mContext,mContext.getString(R.string.kayit_hatasi),1);
                        }
                    }else{
                        Function.showToast(mContext,mContext.getString(R.string.kayit_hatasi),1);
                    }
                } catch (Exception e) {
                    Function.showToast(mContext,mContext.getString(R.string.kayit_hatasi),1);
                }
            }
            @Override
            public void onFailure(Call<GetUserModel> call, Throwable t) {
                Function.showToast(mContext,mContext.getString(R.string.kayit_hatasi),1);
            }
        });
    }
}