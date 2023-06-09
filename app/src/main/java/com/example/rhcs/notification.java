package com.example.rhcs;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class notification extends Application {
    public static final String CHANNEL1 = "channel1";
    public static final String CHANNEL2 = "channel2";


    @Override
    public void onCreate() {
        super.onCreate();
        noti();
    }

    private void noti() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(CHANNEL1, "channel 1", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("channel1");

            NotificationChannel channel2 = new NotificationChannel(CHANNEL2, "channel 2", NotificationManager.IMPORTANCE_HIGH);
            channel2.setDescription("channel2");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }
    }
}
