package com.masterofanalysis;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.LocaleList;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessaging;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Function {
    private static Toast toast;
    private static Dialog loadingDialog;

    public static void showLoading(Activity act,Context context) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (loadingDialog != null && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
                loadingDialog = new Dialog(context);
                loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                loadingDialog.setCancelable(false);
                loadingDialog.setContentView(R.layout.loading);
                loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                loadingDialog.show();
            }
        });
    }
    public static void dismissLoading(Activity act) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (loadingDialog != null && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
            }
        });
    }
    public static void showToast(Context context, String message, int duration){
        if (toast != null) {
            toast.cancel();
        }
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.custom_toast, null);
        toast = new Toast(context);
        toast.setGravity(Gravity.BOTTOM, 0, 16);
        toast.setDuration(duration);
        toast.setView(layout);
        TextView textView = layout.findViewById(R.id.textView);
        textView.setText(message);
        toast.show();
    }
    public static Date stringtoDate(String datestring){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return dateFormat.parse(datestring);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public static void getFirebase_Token(){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        Constants.Firebase_Token = task.getResult();
                    }
                });
    }
    public static String getDeviceId(Context context) {
        String id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return id;
    }
    public static String getTimezone() {
        TimeZone tz = TimeZone.getDefault();
        return tz.getID();
    }
    public static void Get_Permission_Notification(Activity context){
        int permissionState = ContextCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS);
        if (permissionState == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
        }
    }
    public static String getCountry(Context context) {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = context.getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = context.getResources().getConfiguration().locale;
        }
        return locale.getDisplayCountry();
    }
    public static String getDeviceLanguage(Context context) {
        Resources resources = context.getResources();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList locales = resources.getConfiguration().getLocales();
            if (locales.size() > 0) {
                return locales.get(0).getLanguage();
            }
        } else {
            return resources.getConfiguration().locale.getLanguage();
        }

        return null;
    }
}
