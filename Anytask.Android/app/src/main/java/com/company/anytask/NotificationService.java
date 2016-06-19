package com.company.anytask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcel;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {

    private final int UPDATE_INTERVAL = 60 * 1000;
    private Timer timer = new Timer();
    private static final int NOTIFICATION_EX = 1;
    private NotificationManager notificationManager;

    public NotificationService() {}

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        // Code to execute when the service is first created
    }

    @Override
    public void onDestroy() {
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startid) {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        long when = System.currentTimeMillis();

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle("Notification Alert, Click Me!");
        mBuilder.setContentText("Hi, This is Android Notification Detail!");

        Intent resultIntent = new Intent(this, CommentActivity.class);
        resultIntent.putExtra(Intent.EXTRA_TEXT, "Notification");
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(CommentActivity.class);

        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        Notification notification = mBuilder.build();
        notificationManager.notify(NOTIFICATION_EX, notification);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Check if there are updates here and notify if true
            }
        }, 0, UPDATE_INTERVAL);

        return START_STICKY;
    }

    @Override
    public boolean stopService(Intent name) {
        stopService();
        return super.stopService(name);
    }

    private void stopService() {
        if (timer != null)
            timer.cancel();
    }
}
