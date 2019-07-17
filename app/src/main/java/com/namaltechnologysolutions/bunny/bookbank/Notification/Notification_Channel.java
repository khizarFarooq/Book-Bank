package com.namaltechnologysolutions.bunny.bookbank.Notification;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class Notification_Channel extends Application {
    public static final String CHANNEL_id="NotificationServiceChannel";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotifChannel();
    }
    private void createNotifChannel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_id,"Notification Service Channel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
    }
}
