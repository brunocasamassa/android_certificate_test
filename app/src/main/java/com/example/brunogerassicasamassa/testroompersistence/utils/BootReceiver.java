package com.example.brunogerassicasamassa.testroompersistence.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.brunogerassicasamassa.testroompersistence.jobscheduler.JobSchedulerUtils;

public class BootReceiver extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceive(Context context, Intent intent) {
        JobSchedulerUtils.scheduleJob(context);
    }
}
