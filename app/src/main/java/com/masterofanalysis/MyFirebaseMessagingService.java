package com.masterofanalysis;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.masterofanalysis.Activity.activity_Splash;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String token) {
        FirebaseAuth auth=FirebaseAuth.getInstance();
        super.onNewToken(token);
        if (auth.getCurrentUser() !=null ){
            auth.getCurrentUser().getUid();
        }
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        final String ADMIN_CHANNEL_ID ="user_notify";
        Intent intent;
        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");
        String image = remoteMessage.getData().get("imagex");
        String deeplink = remoteMessage.getData().get("deep_linkx");
        if (deeplink.trim().length() > 0 && deeplink != null){
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(deeplink));
        }else{
            intent = new Intent(this, activity_Splash.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int notificationID = new Random().nextInt(3000);

        setupChannels(notificationManager);

        PendingIntent pendingIntent = PendingIntent.getActivity(this , 0, intent,
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        int color = ContextCompat.getColor(this, R.color.genel);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), ADMIN_CHANNEL_ID)
                .setSmallIcon(R.drawable.notify_logo)
                .setContentTitle(title)
                .setStyle(
                        new NotificationCompat.BigTextStyle()
                                .bigText(body))
                .setAutoCancel(true)
                .setColor(color);
        if (image.trim().length() >0 && image != null){
            Bitmap bitmap = getBitmapFromURL(image);
            if (bitmap != null) {
                builder.setStyle(
                        new NotificationCompat.BigPictureStyle()
                                .bigPicture(bitmap)
                                .setBigContentTitle(title)
                                .setSummaryText(body)
                );
            }
        }
        builder.setContentIntent(pendingIntent);
        notificationManager.notify(notificationID, builder.build());

        /*
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), ADMIN_CHANNEL_ID)
                .setSmallIcon(R.drawable.notify_logo)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setColor(color);
        if (image.trim().length() >0 && image != null){
            Bitmap bitmap = getBitmapFromURL(image);
            if (bitmap != null) {
                builder.setStyle(
                        new NotificationCompat.BigPictureStyle()
                                .bigPicture(bitmap)
                );
            }
        }
        builder.setContentIntent(pendingIntent);
        notificationManager.notify(notificationID, builder.build());

         */
    }
    public Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void setupChannels(NotificationManager notificationManager){
        String CHANNEL_ID = "user_notify";
        CharSequence name = "user_notify";
        String Description = "user_notify";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
        }
    }
}