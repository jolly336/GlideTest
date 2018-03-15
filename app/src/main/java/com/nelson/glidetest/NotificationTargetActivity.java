package com.nelson.glidetest;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.NotificationTarget;

/**
 * Created by Zihuatanejo on 16/12/16.
 */
public class NotificationTargetActivity extends Activity {

    public static final int ID_NOTIFICATION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_notification_target);
    }

    public void notification(View view) {
        triggerNotification();
    }

    private void triggerNotification() {
        //build remoteView
        RemoteViews rv = new RemoteViews(this.getPackageName(), R.layout.notification_remoteview);
        rv.setImageViewResource(R.id.iv_notification_icon, R.mipmap.ic_launcher);
        rv.setTextViewText(R.id.tv_notification_headline, "Headline");
        rv.setTextViewText(R.id.tv_notification_short_msg, "Short Message");
        rv.setProgressBar(R.id.progressbar, 100, 10, false);


        //build notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Content Title")
                .setContentText("Content Text")
                .setContent(rv)
                .setPriority(NotificationCompat.PRIORITY_MIN);

        Notification notification = builder.build();

        //set big content view for newer adndroids
        if (Build.VERSION.SDK_INT >= 16) {
            notification.bigContentView = rv;
        }

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(ID_NOTIFICATION, notification);

        //build target provided by glide
        NotificationTarget notificationTarget = new NotificationTarget(
                this, rv, R.id.iv_notification_icon,
                notification, ID_NOTIFICATION);

        Glide.with(this)
                .load("http://i.imgur.com/rFLNqWI.jpg")
                .asBitmap()
                .into(notificationTarget);
    }


}
