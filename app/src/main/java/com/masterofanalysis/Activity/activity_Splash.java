package com.masterofanalysis.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.billingclient.api.Purchase;
import com.masterofanalysis.AppPurchase.PurchaseClass;
import com.masterofanalysis.BuildConfig;
import com.masterofanalysis.Constants;
import com.masterofanalysis.DataModels.AppSettingsModel;
import com.masterofanalysis.DataModels.GetResponseModel;
import com.masterofanalysis.Function;
import com.masterofanalysis.R;
import com.masterofanalysis.api.ApiClient;
import com.masterofanalysis.api.ApiService;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.masterofanalysis.api.userHasSubsInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_Splash extends AppCompatActivity {
    private FirebaseAuth auth;
    LinearLayout lila1;
    RelativeLayout included;
    ImageView imgbakim;
    TextView txtaciklama, kalansure;
    Button containedButton;
    CountDownTimer countDownTimer;
    PurchaseClass purchaseClass;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        lila1 = findViewById(R.id.lila1);
        included = findViewById(R.id.included);
        imgbakim = findViewById(R.id.imgbakim);
        txtaciklama = findViewById(R.id.txtaciklama);
        containedButton = findViewById(R.id.containedButton);
        kalansure = findViewById(R.id.kalansure);
        auth = FirebaseAuth.getInstance();
        apiService = ApiClient.getClient().create(ApiService.class);
        purchaseClass = new PurchaseClass(this, getApplicationContext());
        if (auth.getCurrentUser() != null) {
            post_api(apiService.GetAppSettings(
                    "get_app_settings",
                    auth.getUid(),
                    Function.getTimezone(),
                    Constants.API_KEY));

            Log.e("UUUUUU1", "ergterterte");
        } else {
            post_api(apiService.GetAppSettings(
                    "get_app_settings",
                    null,
                    Function.getTimezone(),
                    Constants.API_KEY));
            Log.e("UUUUUU2", "ergterterte");
        }
    }

    private void post_api(Call<AppSettingsModel> callPost) {
        Log.e("UUUUUU3", "ergterterte");
        callPost.enqueue(new Callback<AppSettingsModel>() {
            @Override
            public void onResponse(Call<AppSettingsModel> call, Response<AppSettingsModel> response) {
                try {
                    Log.e("UUUUUU4", "ergterterte");
                    if (response.isSuccessful()) {
                        Log.e("UUUUUU5", "ergterterte");
                        if (response.body().getResult().equalsIgnoreCase("success")) {
                            AppSettingsModel userModel = response.body();
                            if (userModel.getBan_status() != null)
                                Constants.Ban_Statusboolen = userModel.getBan_status();
                            if (userModel.getPrivacy_Policy_Url() != null)
                                Constants.Privacy_Policy_Url = userModel.getPrivacy_Policy_Url();
                            if (userModel.getVersion_name() != null)
                                Constants.Version_Name = userModel.getVersion_name();
                            if (userModel.getVersion_control() != null)
                                Constants.Version_Control = userModel.getVersion_control();
                            if (userModel.getMaintenance_control() != null)
                                Constants.Maintenance_Control = userModel.getMaintenance_control();
                            if (userModel.getMaintenance_minute() != null)
                                Constants.Maintenance_Minute = userModel.getMaintenance_minute();
                            if (userModel.getYoutube_link() != null)
                                Constants.Youtube_Link = userModel.getYoutube_link();
                            if (userModel.getInstagram_link() != null)
                                Constants.Instagram_Link = userModel.getInstagram_link();
                            if (userModel.getWhatsapp_link() != null)
                                Constants.Whatsapp_Link = userModel.getWhatsapp_link();
                            if (userModel.getTelegram_link() != null)
                                Constants.Telegram_Link = userModel.getTelegram_link();
                            if (userModel.getWebsite_link() != null)
                                Constants.Website_Link = userModel.getWebsite_link();
                            if (userModel.getButton_tr() != null)
                                Constants.button_tr = userModel.getButton_tr();
                            if (userModel.getButton_en() != null)
                                Constants.button_en = userModel.getButton_en();
                            if (userModel.getText_tr() != null)
                                Constants.text_tr = userModel.getText_tr();
                            if (userModel.getText_en() != null)
                                Constants.text_en = userModel.getText_en();
                            if (userModel.getReferral_link() != null)
                                Constants.referral_link = userModel.getReferral_link();
                            if (userModel.getNow_time() != null)
                                Constants.now_time = userModel.getNow_time();
                            if (userModel.getReference_control() != null)
                                Constants.reference_control = userModel.getReference_control();
                            if (userModel.getReference_title_tr() != null)
                                Constants.reference_title_tr = userModel.getReference_title_tr();
                            if (userModel.getReference_title_en() != null)
                                Constants.reference_title_en = userModel.getReference_title_en();
                            if (userModel.getReference_exp_tr() != null)
                                Constants.reference_exp_tr = userModel.getReference_exp_tr();
                            if (userModel.getReference_exp_en() != null)
                                Constants.reference_exp_en = userModel.getReference_exp_en();
                            if (Constants.Maintenance_Control) {
                                lila1.setVisibility(View.GONE);
                                included.setVisibility(View.VISIBLE);
                                if (Locale.getDefault().getLanguage().equalsIgnoreCase("tr")) {
                                    txtaciklama.setText(Constants.text_tr);
                                    containedButton.setText(Constants.button_tr);
                                } else {
                                    txtaciklama.setText(Constants.text_en);
                                    containedButton.setText(Constants.button_en);
                                }
                                long geriSayimSuresiMillis = Function.stringtoDate(Constants.Maintenance_Minute).getTime() - Function.stringtoDate(Constants.now_time).getTime();
                                countDownTimer = new CountDownTimer(geriSayimSuresiMillis, 10) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        kalansure.setText(getString(R.string.kalansure) + "\n" + formatZaman(millisUntilFinished));
                                    }
                                    @Override
                                    public void onFinish() {
                                    }
                                };
                                countDownTimer.start();
                                containedButton.setOnClickListener(view -> {
                                    try {
                                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.referral_link));
                                        startActivity(browserIntent);
                                    } catch (Exception e) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                            finishAffinity();
                                        } else {
                                            ActivityCompat.finishAffinity(activity_Splash.this);
                                        }
                                        System.exit(0);
                                    }
                                });
                                Animation rotation = AnimationUtils.loadAnimation(activity_Splash.this, R.anim.rotate);
                                rotation.setFillAfter(true);
                                imgbakim.startAnimation(rotation);
                            } else {
                                Log.e("UUUUUU6", "ergterterte");
                                //bakımda değil ise
                                //Function.showToast(activity_Splash.this,"Uygulama Bakım Modunda Değil",1);
                                if (Constants.Version_Control) {
                                    Log.e("UUUUUU7", "ergterterte");
                                    if (!Constants.Version_Name.equals(BuildConfig.VERSION_NAME)) {
                                        try {
                                            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(activity_Splash.this)
                                                    .setTitle(getString(R.string.update))
                                                    .setMessage(getString(R.string.update_text))
                                                    .setPositiveButton(getString(R.string.download), null);
                                            AlertDialog dialog = builder.create();
                                            dialog.setCancelable(false);
                                            dialog.getWindow().getDecorView().setBackgroundResource(android.R.color.holo_blue_bright);
                                            dialog.setOnShowListener(dialogInterface -> {
                                                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                                                positiveButton.setOnClickListener(view -> {
                                                    final String appPackageName = getPackageName(); // package name of the app
                                                    try {
                                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                                    } catch (
                                                            android.content.ActivityNotFoundException anfe) {
                                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                                    }
                                                });
                                            });
                                            Window window = dialog.getWindow();
                                            WindowManager.LayoutParams wlp = window.getAttributes();
                                            wlp.gravity = Gravity.BOTTOM;
                                            wlp.y = 200;
                                            wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                                            window.setAttributes(wlp);
                                            dialog.show();
                                        } catch (Exception e) {
                                            Function.showToast(activity_Splash.this, getString(R.string.kayit_hatasi), 1);
                                        }
                                    } else {
                                        Log.e("UUUUUU8", "ergterterte");
                                        //versiyonlar eşit ise
                                        //Function.showToast(activity_Splash.this,"versiyonlar eşit",1);
                                        purchaseClass.isUserHasSubscription(new userHasSubsInterface() {
                                            @Override
                                            public void onPurchasesFetched(List<Purchase> purchases) {
                                                if (auth.getCurrentUser() != null) {
                                                    Log.e("UUUUUU9", "ergterterte");
                                                    if (purchases.size() > 0) {
                                                        Log.e("UUUUUU11", "ergterterte");
                                                        userHasSubs(purchases);
                                                    } else {
                                                        if (Constants.Ban_Statusboolen != null){
                                                            if (Constants.Ban_Statusboolen){
                                                                startActivity(new Intent(activity_Splash.this, activity_Userban.class));
                                                                finish();
                                                            }else{
                                                                startActivity(new Intent(activity_Splash.this, MainActivity.class));
                                                                finish();
                                                            }
                                                        }else{
                                                            Log.e("UUUUUU10", "ergterterte");
                                                            startActivity(new Intent(activity_Splash.this, MainActivity.class));
                                                            finish();
                                                        }
                                                    }
                                                } else {
                                                    Log.e("UUUUUU12", "ergterterte");
                                                    startActivity(new Intent(activity_Splash.this, activity_Login.class));
                                                    finish();
                                                }
                                            }
                                        });
                                    }
                                } else {
                                    Log.e("UUUUUU13", "ergterterte");
                                    //versiyon kontrolü kapalı ise
                                    ///Function.showToast(activity_Splash.this,"versiyon kontrolü kapalı",1);
                                    purchaseClass.isUserHasSubscription(new userHasSubsInterface() {
                                        @Override
                                        public void onPurchasesFetched(List<Purchase> purchases) {
                                            if (auth.getCurrentUser() != null) {
                                                Log.e("UUUUUU14", "ergterterte");
                                                Log.e("EEEEEEEE", purchases.size() + " b");
                                                if (purchases.size() > 0) {
                                                    Log.e("UUUUUU15", "ergterterte");
                                                    Log.e("EEEEEEEEifff", purchases.size() + " b");
                                                    userHasSubs(purchases);
                                                } else {
                                                    if (Constants.Ban_Statusboolen != null){
                                                        if (Constants.Ban_Statusboolen){
                                                            startActivity(new Intent(activity_Splash.this, activity_Userban.class));
                                                            finish();
                                                        }else{
                                                            startActivity(new Intent(activity_Splash.this, MainActivity.class));
                                                            finish();
                                                        }
                                                    }else{
                                                        Log.e("UUUUUU10", "ergterterte");
                                                        startActivity(new Intent(activity_Splash.this, MainActivity.class));
                                                        finish();
                                                    }
                                                }
                                            } else {
                                                Log.e("UUUUUU17", "ergterterte");
                                                startActivity(new Intent(activity_Splash.this, activity_Login.class));
                                                finish();
                                            }
                                        }
                                    });
                                }
                            }
                        } else {
                            Log.e("UUUUUU18", "ergterterte");
                            Function.showToast(activity_Splash.this, getString(R.string.kayit_hatasi), 1);
                            auth.signOut();
                            startActivity(new Intent(activity_Splash.this, activity_Login.class));
                            finish();
                        }
                    } else {
                        Function.showToast(activity_Splash.this, getString(R.string.kayit_hatasi), 1);
                    }
                } catch (Exception e) {
                    Function.showToast(activity_Splash.this, getString(R.string.kayit_hatasi), 1);
                }
            }

            @Override
            public void onFailure(Call<AppSettingsModel> call, Throwable t) {
                Log.e("UUUUUU19", "ergterterte");
                Function.showToast(activity_Splash.this, getString(R.string.kayit_hatasi), 1);
            }
        });
    }

    private void userHasSubs(List<Purchase> a) {
        Log.e("EEEEEEEEuserHasSubs", a.size() + " b");
        List<String> productIds = new ArrayList<>();
        List<String> purchaseTokens = new ArrayList<>();
        for (Purchase b : a) {
            productIds.addAll(b.getProducts());
            purchaseTokens.add(b.getPurchaseToken());
        }
        Map<String, Object> userData = new HashMap<>();
        userData.put("uid", auth.getUid());
        userData.put("action", "userHasSubs");
        userData.put("api_key", Constants.API_KEY);
        userData.put("productIds", productIds);
        userData.put("purchaseTokens", purchaseTokens);
        Call<GetResponseModel> call = apiService.userHasSubs(userData);
        call.enqueue(new Callback<GetResponseModel>() {
            @Override
            public void onResponse(Call<GetResponseModel> call, Response<GetResponseModel> response) {
                if (response.isSuccessful()) {
                    if (Constants.Ban_Statusboolen != null){
                        if (Constants.Ban_Statusboolen){
                            startActivity(new Intent(activity_Splash.this, activity_Userban.class));
                            finish();
                        }else{
                            startActivity(new Intent(activity_Splash.this, MainActivity.class));
                            finish();
                        }
                    }else{
                        startActivity(new Intent(activity_Splash.this, MainActivity.class));
                        finish();
                    }
                } else {
                    Function.showToast(getApplicationContext(), getString(R.string.kayit_hatasi), 1);
                }
            }
            @Override
            public void onFailure(Call<GetResponseModel> call, Throwable t) {
                Function.showToast(getApplicationContext(), getString(R.string.kayit_hatasi), 1);
            }
        });
    }
    private String formatZaman(long millis) {
        long gun = TimeUnit.MILLISECONDS.toDays(millis);
        long saat = TimeUnit.MILLISECONDS.toHours(millis) - TimeUnit.DAYS.toHours(gun);
        long dakika = TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis));
        long saniye = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));
        return String.format("%02d:%02d:%02d:%02d", gun, saat, dakika, saniye);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("DESTROYSPLASH", "destroy");
        purchaseClass.destroyBillingClient();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

}