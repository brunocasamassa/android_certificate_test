package com.example.brunogerassicasamassa.testroompersistence.jobscheduler;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.example.brunogerassicasamassa.testroompersistence.R;
import com.example.brunogerassicasamassa.testroompersistence.utils.SessionManager;

public class NotifyLocationService extends Service {

    private SessionManager sessionManager;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        sessionManager = new SessionManager(getApplicationContext());

        Long latitude = sessionManager.getCoordinate("latitude");
        Long longitude = sessionManager.getCoordinate("longitude");

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(latitude.toString())
                .setSubText(longitude.toString())
                .setSmallIcon(R.drawable.ic_notify_icon);


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());

        return super.onStartCommand(intent, flags, startId);


    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
