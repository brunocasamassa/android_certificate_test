package com.example.brunogerassicasamassa.testroompersistence.jobscheduler;

import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CustomJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        Intent service = new Intent(getApplicationContext(), NotifyLocationService.class);
        getApplicationContext().startService(service);
        JobSchedulerUtils.scheduleJob(getApplicationContext()); // reschedule the job
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
