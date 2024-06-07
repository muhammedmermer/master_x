package com.masterofanalysis.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;
import com.masterofanalysis.Constants;
import com.masterofanalysis.DataModels.UserModel;
import com.masterofanalysis.Function;
import com.masterofanalysis.R;
import com.masterofanalysis.api.ApiClient;
import com.masterofanalysis.api.ApiService;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class activity_Login extends AppCompatActivity {
    private FirebaseAuth auth;
    private GoogleSignInClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    CardView cardview_login,cardview_iletisim;
    ImageView imginstagram,imgwhatsapp,imgtelegram,imgyoutube,imgchrome;
    TextView privacy_policy;
    CheckBox checkbox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth=FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        Function.getFirebase_Token();
        if (Locale.getDefault().getLanguage().equalsIgnoreCase("tr")){
            FirebaseMessaging.getInstance().subscribeToTopic("tr");
        }else{
            FirebaseMessaging.getInstance().subscribeToTopic("en");
        }
        mGoogleApiClient = GoogleSignIn.getClient(this, gso);
        cardview_login = findViewById(R.id.cardview_login);
        cardview_iletisim = findViewById(R.id.cardview_iletisim);
        imginstagram = findViewById(R.id.imginstagram);
        imgwhatsapp = findViewById(R.id.imgwhatsapp);
        imgtelegram = findViewById(R.id.imgtelegram);
        imgyoutube = findViewById(R.id.imgyoutube);
        imgchrome = findViewById(R.id.imgchrome);
        privacy_policy = findViewById(R.id.privacy_policy);
        checkbox = findViewById(R.id.checkbox);
        ClickableSpan linkClick = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity_Login.this, Activity_PrivacyPolicy.class));
                view.invalidate();
            }
        };
        if (Locale.getDefault().getLanguage().equalsIgnoreCase("tr")) {
            privacy_policy.setHighlightColor(ContextCompat.getColor(this, R.color.black));
            Spannable spannableString = new SpannableString(getString(R.string.sign_in_privacy));
            spannableString.setSpan(linkClick, 0, 19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            privacy_policy.setText(spannableString, TextView.BufferType.SPANNABLE);
            privacy_policy.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            privacy_policy.setHighlightColor(ContextCompat.getColor(this, R.color.black));
            Spannable spannableString = new SpannableString(getString(R.string.sign_in_privacy));
            spannableString.setSpan(linkClick, 13, 27, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            privacy_policy.setText(spannableString, TextView.BufferType.SPANNABLE);
            privacy_policy.setMovementMethod(LinkMovementMethod.getInstance());
        }
        cardview_login.setOnClickListener(view -> {
            if (checkbox.isChecked()){
                signIn();
            }else{
                Function.showToast(getApplicationContext(),getString(R.string.sign_in_privacy_false),1);
            }
        });
        if (Constants.Instagram_Link != null && Constants.Instagram_Link.length() > 1){
            cardview_iletisim.setVisibility(View.VISIBLE);
            imginstagram.setVisibility(View.VISIBLE);
        }
        if (Constants.Whatsapp_Link != null && Constants.Whatsapp_Link.length() > 1){
            cardview_iletisim.setVisibility(View.VISIBLE);
            imgwhatsapp.setVisibility(View.VISIBLE);
        }
        if (Constants.Telegram_Link != null && Constants.Telegram_Link.length() > 1){
            cardview_iletisim.setVisibility(View.VISIBLE);
            imgtelegram.setVisibility(View.VISIBLE);
        }
        if (Constants.Youtube_Link != null && Constants.Youtube_Link.length() > 1){
            cardview_iletisim.setVisibility(View.VISIBLE);
            imgyoutube.setVisibility(View.VISIBLE);
        }
        if (Constants.Website_Link != null && Constants.Website_Link.length() > 1){
            cardview_iletisim.setVisibility(View.VISIBLE);
            imgchrome.setVisibility(View.VISIBLE);
        }
        imginstagram.setOnClickListener(view -> {
            String appUri = "https://instagram.com/_u/"+Constants.Instagram_Link;
            Uri browserUri = Uri.parse("https://instagram.com/"+Constants.Instagram_Link);
            try {
                PackageManager pm = getPackageManager();
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
            }catch (Exception e){
            }
        });
        imgtelegram.setOnClickListener(view -> {
            try {
                Intent telegram = new Intent(Intent.ACTION_VIEW , Uri.parse("https://telegram.me/"+Constants.Telegram_Link));
                startActivity(telegram);
            }catch (Exception e){
            }
        });
        imgwhatsapp.setOnClickListener(view -> {
            String url = "https://api.whatsapp.com/send?phone=" + Constants.Whatsapp_Link;
            try {
                PackageManager pm = getPackageManager();
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
            }catch (Exception e){
            }
        });
    }
    private void signIn() {
        Intent signInIntent = mGoogleApiClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Function.showToast(activity_Login.this,getString(R.string.yetkilendirmehatasi),1);
                        } else {
                            Save_User_Login();
                        }
                    }
                });
    }
    private void Save_User_Login(){
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<UserModel> callPost = apiService.CreateUser(
                "create_user",
                Constants.API_KEY,
                auth.getUid(),
                auth.getCurrentUser().getDisplayName(),
                auth.getCurrentUser().getEmail(),
                Function.getCountry(activity_Login.this),
                Constants.Firebase_Token,
                Function.getDeviceId(activity_Login.this),
                Function.getDeviceLanguage(activity_Login.this),
                Build.VERSION.RELEASE,
                Build.MODEL);
        callPost.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                try{
                    if (response.isSuccessful()) {
                        if (response.body().getResult().equalsIgnoreCase("success")){
                            if(response.body().getReferans_id() != null)
                                Constants.Referans_id = response.body().getReferans_id();
                            if(response.body().getBan_status() != null)
                                Constants.Ban_Status = response.body().getBan_status();
                            if(response.body().getNow_time() != null)
                                Constants.now_time = response.body().getNow_time();
                            Date now = Function.stringtoDate(Constants.now_time);
                            Date ban = Function.stringtoDate(Constants.Ban_Status);
                            if (ban.after(now)) {
                                startActivity(new Intent(activity_Login.this, activity_Userban.class));
                            }else{
                                startActivity(new Intent(activity_Login.this, MainActivity.class));
                            }
                        }else{
                            Function.showToast(activity_Login.this,getString(R.string.kayit_hatasi),1);
                            Log.e("hata","1");
                        }
                    } else {
                        Function.showToast(activity_Login.this,getString(R.string.kayit_hatasi),1);
                        Log.e("hata","2");
                    }
                }catch (Exception e){
                    Function.showToast(activity_Login.this,getString(R.string.kayit_hatasi),1);
                    Log.e("hata","3");
                }
            }
            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Function.showToast(activity_Login.this,getString(R.string.kayit_hatasi),1);
                Log.e("hata","4");
            }
        });
    }
}